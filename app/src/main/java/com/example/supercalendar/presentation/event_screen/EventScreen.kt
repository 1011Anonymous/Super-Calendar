package com.example.supercalendar.presentation.event_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supercalendar.R
import com.example.supercalendar.ui.theme.topAppBarTextStyle

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EventScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "新建事件",
                        style = topAppBarTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Back to Home")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save Event")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            val pagerState = rememberPagerState(initialPage = 0) {
                4
            }

            var selectedTab by remember {
                mutableIntStateOf(pagerState.currentPage)
            }

            LaunchedEffect(key1 = selectedTab) {
                pagerState.scrollToPage(selectedTab)
            }

            LaunchedEffect(key1 = pagerState.currentPage) {
                selectedTab = pagerState.currentPage
            }

            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.padding(8.dp)
                ) {
                for (index in 0 until pagerState.pageCount) {
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    ) {
                        val id = when (index) {
                            0 -> R.drawable.outline_notifications_24
                            1 -> R.drawable.outline_event_note_24
                            2 -> R.drawable.outline_cake_24
                            else -> R.drawable.outline_card_travel_24
                        }

                        val text = when (index) {
                            0 -> "提醒"
                            1 -> "日程"
                            2 -> "生日"
                            else -> "出行"
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(30.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = null
                            )
                        }

                        Text(
                            text = text,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            HorizontalPager(state = pagerState) {
                ScreenOne(pageNumber = it.toString())
            }
        }
    }
}

@Composable
fun ScreenOne(pageNumber: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = pageNumber)
    }
}



@Preview
@Composable
fun PreviewPager() {
    EventScreen()
}