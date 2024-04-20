package com.example.supercalendar.presentation.event_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supercalendar.R
import com.example.supercalendar.constant.Const
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils
import java.time.LocalDate
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayScreen() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        var text by remember {
            mutableStateOf("")
        }
        val focusRequester = remember {
            FocusRequester()
        }

        val datePickerState = rememberDatePickerState()
        var showDatePicker by remember {
            mutableStateOf(false)
        }

        var dateText by remember {
            mutableStateOf("${LocalDate.now().year}年" +
                    "${LocalDate.now().monthValue}月" +
                    "${LocalDate.now().dayOfMonth}日" +
                    Const.chineseNumerals[LocalDate.now().dayOfWeek.value])
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDate = Calendar.getInstance().apply {
                                this.timeInMillis = datePickerState.selectedDateMillis!!
                            }
                            val localDate = DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)
                            dateText = DateUtils.dateToString(localDate)
                            showDatePicker = false
                        }
                    ) { Text("确定") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) { Text("取消") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            placeholder = { Text(text = "请输入姓名") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp))
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {

                }
            ),
            trailingIcon = {
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = null
                    )
                }
            },
            textStyle = taskTextStyle
        )
        Spacer(modifier = Modifier.size(20.dp))

        Icon(painter = painterResource(id = R.drawable.outline_access_time_24), contentDescription = null)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showDatePicker = true }
            ) {
                Text(text = dateText)
                Spacer(modifier = Modifier.width(250.dp))
            }
        }
        Divider(modifier = Modifier.padding(bottom = 20.dp))

        Icon(painter = painterResource(id = R.drawable.outline_notifications_active_24), contentDescription = null)

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "任务发生当天")
            Spacer(modifier = Modifier.width(260.dp))
        }



    }
}

@Preview
@Composable
fun PreviewBirthday() {
    BirthdayScreen()
}