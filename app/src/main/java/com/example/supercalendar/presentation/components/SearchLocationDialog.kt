package com.example.supercalendar.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supercalendar.constant.Const.Companion.UNKNOWN
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.ui.theme.taskTextStyle

@Composable
fun SearchLocationDialog(
    openDialog: Boolean,
    onClose: () -> Unit,
    weatherViewModel: WeatherViewModel
) {
    var city by remember {
        mutableStateOf("")
    }

    val focusRequester = remember {
        FocusRequester()
    }
    val context = LocalContext.current

    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = "城市",
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                LaunchedEffect(key1 = Unit) {
                    focusRequester.requestFocus()
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        placeholder = {
                            Text(
                                text = "更换城市",
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        shape = RectangleShape,
                        modifier = Modifier.focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (city.isNotBlank()) {
                                    weatherViewModel.getLocationByName(city)
                                    onClose()
                                    //focusManager.clearFocus()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "请填写城市",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { city = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = taskTextStyle
                    )

                }
            },
            onDismissRequest = {
                onClose()
                city = ""
            },
            confirmButton = {
                TextButton(onClick = {
                    if (city.isNotBlank()) {
                        weatherViewModel.getLocationByName(city)
                        onClose()
                        //focusManager.clearFocus()
                    } else {
                        Toast.makeText(
                            context,
                            "请填写城市",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = "保存")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onClose()
                    city = ""
                }) {
                    Text(text = "取消")
                }
            }
        )
    }
}