package com.depromeet.whatnow.ui.promiseAdd

import android.annotation.SuppressLint
import android.os.Build
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.depromeet.whatnow.component.WhatNowSimpleTopBar
import com.depromeet.whatnow.ui.R
import com.depromeet.whatnow.ui.theme.MaterialColors
import com.depromeet.whatnow.ui.theme.WhatNowTheme
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PromiseScreen(
    viewModel: PromiseAddViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    var screenState by remember { mutableStateOf(PromiseScreenState()) }

    val selectedDate = remember { mutableStateOf("") }
    val selectedClock = remember { mutableStateOf("") }
    val inputPlaceName = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            WhatNowSimpleTopBar(
                onBack = onBack,
                titleRes = R.string.promise_title,
            )
        },
        bottomBar = {
            WhatNowPromiseAddBottomBar(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
                buttonOnClick = {
                    if (!screenState.isClockVisible && !screenState.isPlaceVisible) {
                        screenState = screenState.copy(
                            isSetDateValue = true,
                            isClockVisible = true,
                            isClockDataSet = true
                        )
                    } else if (!screenState.isPlaceVisible) {
                        screenState = screenState.copy(
                            isClockVisible = false,
                            isPlaceVisible = true,
                            isPlaceDataSet = true,
                        )
                    } else {
                        //TODO 최종만들기 버튼
                    }
                },
                resetOnClick = {
                    screenState = screenState.copy(
                        isSetDateValue = false,
                        isPlaceVisible = false,
                        isClockVisible = false,
                        isClockDataSet = false,
                        isPlaceDataSet = false

                    )
                })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
            ) {
                if (screenState.isSetDateValue) {
                    setMakeBox(
                        onClick = {
                            screenState = screenState.copy(
                                isSetDateValue = false,
                                isPlaceVisible = false,
                                isClockVisible = false
                            )
                        },
                        R.string.promise_calendar,
                        null,
                        R.drawable.calendar,
                        true,
                        selectedDate.value
                    )
                } else {
                    Calendar(
                        onDateChanged = {
                            selectedDate.value = it
                        }
                    )
                    screenState = screenState.copy(isClockDataSet = false)
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (!screenState.isClockDataSet && !screenState.isClockVisible) {
                    setMakeBox(
                        onClick = {
                            screenState = screenState.copy(
                                isSetDateValue = true,
                                isClockVisible = true,
                                isClockDataSet = true
                            )
                        },
                        R.string.promise_time,
                        R.string.promise_time_msg,
                        R.drawable.clock,
                        false, null
                    )
                } else if (screenState.isClockDataSet && !screenState.isClockVisible) {
                    setMakeBox(
                        onClick = {
                            screenState = screenState.copy(
                                isSetDateValue = true,
                                isClockVisible = true,
                                isClockDataSet = true,
                                isPlaceDataSet = true,
                                isPlaceVisible = false
                            )
                        },
                        R.string.promise_time,
                        null,
                        R.drawable.clock,
                        true, selectedClock.value
                    )
                } else {
                    setClock(
                        onTimeChanged = {
                            selectedClock.value = it
                        }
                    )
                    screenState = screenState.copy(isPlaceDataSet = false)
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (!screenState.isPlaceDataSet && !screenState.isPlaceVisible) {
                    setMakeBox(
                        onClick = {
                            screenState = screenState.copy(
                                isSetDateValue = true,
                                isClockVisible = false,
                                isClockDataSet = true,
                                isPlaceVisible = true
                            )
                        },
                        R.string.promise_place,
                        R.string.promise_place_msg,
                        R.drawable.map,
                        false, null
                    )
                } else if (screenState.isPlaceDataSet && !screenState.isPlaceVisible) {
                    setMakeBox(
                        onClick = {
                            screenState = screenState.copy(
                                isSetDateValue = true,
                                isClockVisible = false,
                                isClockDataSet = true,
                                isPlaceVisible = true
                            )
                        },
                        R.string.promise_place,
                        R.string.promise_place_msg,
                        R.drawable.map,
                        true, null
                    )
                } else {
                    PlaceList(viewModel = viewModel, onPlaceList = {

                    })
                }

                Spacer(modifier = Modifier.height(18.dp)) // 간격 설정
            }
        }
    }
}

data class PromiseScreenState(
    val isSetDateValue: Boolean = false,
    val isClockDataSet: Boolean = false,
    val isPlaceDataSet: Boolean = false,
    val isClockVisible: Boolean = false,
    val isPlaceVisible: Boolean = false,
)

//@Composable
//fun PlaceList(placeList: ArrayList<PromiseAddPlace>) {
//    for (x in placeList) {
//        SearchPlaceList(x)
//    }
//}


@Composable
fun Greeting(resId: Int, textSize: Int, textColor: Color) {
    Text(
        text = LocalContext.current.getString(resId),
        fontSize = textSize.sp,
        color = textColor
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(onDateChanged: (String) -> Unit) {
    WhatNowTheme {
        Box(
            modifier = Modifier
                .padding(start = 15.dp)
                .width(350.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            val context = LocalContext.current

            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    val themedContext = ContextThemeWrapper(context, R.style.CreateProfileTheme)
                    val view = LayoutInflater.from(themedContext)
                        .inflate(R.layout.item_promise_calendar, null)
                    val datePicker = view.findViewById<DatePicker>(R.id.datePicker1)

                    // 현재 날짜로 DatePicker 초기화
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    datePicker.updateDate(year, month, day)

                    // 날짜를 바꾸지 않으면 현재 날짜 입력
                    val selectedDateString = String.format("%d월 %d일 약속", month, day)
                    onDateChanged(selectedDateString)

                    datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                        val selectedDateString =
                            String.format("%d월 %d일 약속", monthOfYear, dayOfMonth)
                        onDateChanged(selectedDateString)
                    }

                    view
                }
            )
        }
    }
}

@Composable
fun PlaceList(viewModel: PromiseAddViewModel, onPlaceList: (List<PromiseAddPlace>) -> Unit) {
    var text by remember { mutableStateOf("") }
    PlaceEdit(onPlaceChanged = {
        text = it
        viewModel.placeData.value.placeList?.let { it1 -> onPlaceList(it1) }
        //TODO viewmodel 데이터 변경
    },
        R.string.promise_place_select,
        R.string.promise_place_msg,
        R.drawable.whitemap)

}

@Composable
fun PlaceEdit(onPlaceChanged: (String) -> Unit, titleResId: Int, messageResId: Int, iconRes: Int) {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(74.dp)
            .background(
                color = WhatNowTheme.colors.whatNowBlack,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                text = LocalContext.current.getString(titleResId),
                style = WhatNowTheme.typography.body4,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .padding(start = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        modifier = Modifier
                            .height(30.dp)
                            .width(238.dp),
                        value = text,
                        onValueChange = {
                            text = it
                            onPlaceChanged(text)
                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp // 원하는 크기로 설정
                        ),
                        decorationBox = {
                            Box(
                                modifier = Modifier.padding(1.dp)
                            ) {
                                if (text.isBlank()) {
                                    Text(
                                        text = LocalContext.current.getString(R.string.promise_place_msg),
                                        style = TextStyle(color = Color.White,
                                            fontSize = 16.sp)
                                    )
                                }
                                it()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchPlaceList(playList: PromiseAddPlace) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .height(72.dp),
            colors = CardDefaults.cardColors(containerColor = WhatNowTheme.colors.gray50)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    playList.placeTitle?.let {
                        androidx.compose.material3.Text(
                            text = it,
                            style = WhatNowTheme.typography.body1,
                            color = WhatNowTheme.colors.whatNowBlack
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = WhatNowTheme.colors.gray700
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        playList.placeAddress?.let {
                            androidx.compose.material3.Text(
                                text = it,
                                style = WhatNowTheme.typography.caption1,
                                color = WhatNowTheme.colors.gray700
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun setClock(onTimeChanged: (String) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        AndroidView(
            factory = { context ->
                val themedContext = ContextThemeWrapper(context, R.style.CustomTimePicker)
                val view =
                    LayoutInflater.from(themedContext).inflate(R.layout.item_promise_clock, null)

                val timePicker = view.findViewById<TimePicker>(R.id.timePicker)

                // 바꾸지 않고 바로 하면 현재 시각 입력
                onTimeChanged(clockFormatting(timePicker.hour, timePicker.minute))

                timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                    onTimeChanged(clockFormatting(hourOfDay, minute))
                }
                view
            }
        )
    }
}

// 시각 포매팅
fun clockFormatting(hourOfDay: Int, minute: Int): String {
    val formattedHour = if (hourOfDay >= 12) {
        val hour = if (hourOfDay > 12) hourOfDay - 12 else hourOfDay
        String.format("%02d", hour)
    } else {
        String.format("%02d", hourOfDay)
    }

    val formattedMinute = String.format("%02d", minute)
    val amPm = if (hourOfDay >= 12) "오후" else "오전"

    return "$amPm $formattedHour 시 $formattedMinute 분"
}

@Composable
fun setMakeBox(
    onClick: () -> Unit,
    titleResId: Int,
    messageResId: Int?,
    iconRes: Int,
    isSelected: Boolean,
    message: String?,
) {

    val backgroundColor = if (isSelected) MaterialColors.onPrimary else WhatNowTheme.colors.gray50
    val borderColor = if (isSelected) MaterialColors.onPrimary else WhatNowTheme.colors.whatNowBlack

    if (!isSelected) {
        IncompleteContent(titleResId)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .border(
                width = if (isSelected) 0.dp else 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp)) // 간격 설정

            if (messageResId == null && message != null) {
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = WhatNowTheme.colors.gray900
                )
            } else {
                Greeting(messageResId!!, 14, WhatNowTheme.colors.gray500)
            }

        }
    }
}

@Composable
fun IncompleteContent(titleResId: Int) {
    Greeting(titleResId, 12, WhatNowTheme.colors.gray900)
    Spacer(modifier = Modifier.height(6.dp))
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(device = Devices.PIXEL_2)
@Composable
fun DefaultPreview() {
    WhatNowTheme {
        PromiseScreen(onBack = {})
    }
}

@Composable
fun WhatNowPromiseAddBottomBar(
    resetOnClick: () -> Unit,
    buttonOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomPanelHeight = 80.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(bottomPanelHeight)
                .fillMaxWidth(),
            color = WhatNowTheme.colors.gray900,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)

                ) {
                    Surface(modifier = Modifier
                        .align(Alignment.TopStart)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {

                            }
                        ),
                        color = WhatNowTheme.colors.gray900) {
                        Text(
                            modifier = Modifier
                                .padding(top = 14.5.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                    onClick = resetOnClick
                                ),
                            text = LocalContext.current.getString(R.string.promise_bottom_reset),
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            textAlign = TextAlign.Center,
                            color = MaterialColors.onPrimary
                        )
                    }
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .width(74.dp)
                            .height(56.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = buttonOnClick
                            ),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.background(WhatNowTheme.colors.whatNowPurple)
                        ) {
                            Text(
                                text = LocalContext.current.getString(R.string.promise_bottom_button_next),
                                textAlign = TextAlign.Center,
                                color = Color(0xEEEEEFEE)
                            )
                        }
                    }
                }
            }
        }
    }
}