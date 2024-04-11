package com.example.supercalendar.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supercalendar.constant.Const.Companion.months

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonthPicker(
    visible: Boolean,
    currentMonth: Int,
    currentYear: Int,
    confirmClicked: (Int, Int) -> Unit,
    cancelClicked: () -> Unit,
) {
    var month by remember {
        mutableStateOf(months[currentMonth])
    }

    var year by remember {
        mutableIntStateOf(currentYear)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (visible) {
        AlertDialog(
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource
                                ) {
                                    year--
                                },
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = null,
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = year.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource
                                ) {
                                    year++
                                },
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                    ) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.Center,
                            maxItemsInEachRow = 3,
                        ) {
                            months.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource
                                        ) {
                                            month = it
                                        }
                                        .background(color = Color.Transparent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val animatedSize by animateDpAsState(
                                        targetValue = if (month == it) 60.dp else 0.dp,
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing,
                                        ), label = ""
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(animatedSize)
                                            .background(
                                                color = if (month == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                                                shape = CircleShape
                                            )
                                    )

                                    Text(
                                        text = it,
                                        color = if (month == it) Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            },

            onDismissRequest = { cancelClicked() },

            dismissButton = {
                TextButton(
                    onClick = {
                        cancelClicked()
                    }
                ) {
                    Text(text = "取消")
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        confirmClicked(
                            months.indexOf(month) + 1,
                            year
                        )
                    }
                ) {
                    Text(text = "确认")
                }
            },
        )
    }
}