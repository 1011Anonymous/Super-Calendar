package com.example.supercalendar.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.supercalendar.domain.model.holiday.Holiday
import com.example.supercalendar.presentation.HolidayViewModel
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.utils.convertLocalDateToHoliday1
import com.example.supercalendar.utils.convertLocalDateToHoliday2
import com.example.supercalendar.utils.getWeekOfYear
import com.example.supercalendar.utils.rememberFirstMostVisibleMonth
import com.example.supercalendar.utils.removeFromName
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarView(
    viewModel: HolidayViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel,
) {
    val currentMonth = remember {
        YearMonth.now()
    }
    val startMonth = remember {
        currentMonth.minusMonths(600)
    }
    val endMonth = remember {
        currentMonth.plusMonths(600)
    }

    val firstDayOfWeek by homeViewModel.firstDayOfWeek.collectAsState(initial = DayOfWeek.MONDAY)
    val daysOfWeek = daysOfWeek(firstDayOfWeek = firstDayOfWeek)

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonthInScrolling = rememberFirstMostVisibleMonth(state = state, viewportPercent = 90f)
    val today = remember { LocalDate.now() }
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val holidayList by viewModel.holidays.collectAsState()
    val visibleMonthState = remember { mutableStateOf(visibleMonthInScrolling) }
    val context = LocalContext.current

    val selectedIsHoliday by homeViewModel.displayHoliday.collectAsState(initial = false)
    val selectedIsLunar by homeViewModel.displayLunar.collectAsState(initial = false)
    val selectedIsFestival by homeViewModel.displayFestival.collectAsState(initial = false)
    val selectedIsWeekday by homeViewModel.displayWeekday.collectAsState(initial = false)
    val selectedIsHighlight by homeViewModel.highlightWeekendsState.collectAsState(initial = false)

    LaunchedEffect(key1 = Unit) {
        viewModel.loadHolidaysForSurroundingMonths(currentMonth)
    }

    LaunchedEffect(visibleMonthInScrolling) {
        if (visibleMonthState.value != visibleMonthInScrolling) {
            viewModel.loadHolidaysForSurroundingMonths(visibleMonthInScrolling.yearMonth)
            visibleMonthState.value = visibleMonthInScrolling
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(key1 = daysOfWeek) {
        state.firstDayOfWeek = daysOfWeek.first()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        CalendarTitle(
            currentMonth = visibleMonthInScrolling.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    //homeViewModel.setVisibleMonth(visibleMonthInScrolling.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    //homeViewModel.setVisibleMonth(visibleMonthInScrolling.yearMonth.nextMonth)
                }
            },
            onClick = {
                showDialog = true
            },
        )

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day,
                    daysOfWeek,
                    isSelected = selectedDate == day.date,
                    isToday = today == day.date,
                    isWeekend = day.date.dayOfWeek == DayOfWeek.SATURDAY || day.date.dayOfWeek == DayOfWeek.SUNDAY,
                    isHighlight = selectedIsHighlight,
                    isDisplayHoliday = selectedIsHoliday,
                    isDisplayFestival = selectedIsFestival,
                    isDisplayLunar = selectedIsLunar,
                    isDisplayDayOfWeek = selectedIsWeekday,
                    holiday = holidayList.firstOrNull { it.date == day.date.toString() }
                ) { calendarDay ->
                    selectedDate = if (selectedDate == calendarDay.date) null else calendarDay.date
                }
            },
            monthHeader = {
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            },
            //userScrollEnabled = false
        )
    }
    MonthPicker(
        visible = showDialog,
        currentMonth = Calendar.getInstance().get(Calendar.MONTH),
        currentYear = Year.now().value,
        confirmClicked = { month, year ->
            coroutineScope.launch {
                state.scrollToMonth(YearMonth.of(year, month))
                //homeViewModel.setVisibleMonth(YearMonth.of(year, month))
                showDialog = false
            }
        },
        cancelClicked = {
            showDialog = false
        },
    )

    LaunchedEffect(key1 = homeViewModel.isGoBackToday.value) {
        if (homeViewModel.isGoBackToday.value) {
            state.scrollToMonth(currentMonth)
            homeViewModel.setIsGoBackToday(false)
        }
    }

    LaunchedEffect(key1 = visibleMonthInScrolling) {
        homeViewModel.setVisibleMonth(visibleMonthInScrolling.yearMonth)
    }

}

@Composable
fun Day(
    day: CalendarDay,
    daysOfWeek: List<DayOfWeek>,
    isSelected: Boolean,
    isToday: Boolean,
    isWeekend: Boolean,
    isHighlight: Boolean,
    isDisplayHoliday: Boolean,
    isDisplayFestival: Boolean,
    isDisplayLunar: Boolean,
    isDisplayDayOfWeek: Boolean,
    holiday: Holiday?,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = Modifier
            .then(
                if (isToday) {
                    Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clickable(
                            enabled = day.position == DayPosition.MonthDate,
                            onClick = { onClick(day) },
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                } else {
                    Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        )
                        .clickable(
                            enabled = day.position == DayPosition.MonthDate,
                            onClick = { onClick(day) },
                        )
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (day.position == DayPosition.MonthDate) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = when {
                    isSelected -> Color.White
                    isWeekend -> if (isHighlight) MaterialTheme.colorScheme.inversePrimary else Color.Black
                    else -> Color.Black
                }
            )
        } else {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = Color.Gray
            )
        }

        holiday?.let {
            if (day.position == DayPosition.MonthDate) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    text = if ((it.holiday == convertLocalDateToHoliday1(day.date) || it.holiday == convertLocalDateToHoliday2(
                            day.date
                        )) && isDisplayFestival
                    ) removeFromName(it.name) else if (isDisplayLunar) it.lunarday else "",
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 10.sp
                )
            } else {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    text = if ((it.holiday == convertLocalDateToHoliday1(day.date) || it.holiday == convertLocalDateToHoliday2(
                            day.date
                        )) && isDisplayFestival
                    ) removeFromName(it.name) else if (isDisplayLunar) it.lunarday else "",
                    color = Color.Gray,
                    fontSize = 10.sp
                )
            }
            if (day.date.year <= Year.now().value && isDisplayHoliday) {
                if (it.daycode == 1) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        color = if (isSelected) Color.White else Color.Green,
                        text = "休",
                        fontSize = 10.sp
                    )

                } else if (it.daycode == 3) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        color = if (isSelected) Color.White else Color.Red,
                        text = "班",
                        fontSize = 10.sp
                    )
                }
            }
        }

        if (isDisplayDayOfWeek) {
            if (day.date.dayOfWeek == daysOfWeek.first()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    color = if (isSelected) Color.White else Color.Gray,
                    text = "${getWeekOfYear(day.date, daysOfWeek.first())}",
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.SIMPLIFIED_CHINESE),
            )
        }
    }
}