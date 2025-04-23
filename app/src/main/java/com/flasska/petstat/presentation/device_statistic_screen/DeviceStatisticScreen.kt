package com.flasska.petstat.presentation.device_statistic_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flasska.petstat.presentation.theme.ui.Black
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasska.petstat.R
import com.flasska.petstat.presentation.theme.ui.GreenForBorder
import com.flasska.petstat.presentation.theme.ui.GreenForTextButton
import com.flasska.petstat.presentation.theme.ui.GreenJungli
import com.flasska.petstat.presentation.theme.ui.Mint
import com.flasska.petstat.presentation.theme.ui.Platina
import com.flasska.petstat.presentation.theme.ui.Red
import com.flasska.petstat.presentation.theme.ui.White
import com.flasska.petstat.presentation.theme.ui.Yellow
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import com.flasska.petstat.presentation.theme.ui.Black2
import com.flasska.petstat.presentation.theme.ui.Gray
import com.flasska.petstat.presentation.theme.ui.LightBlue
import com.flasska.petstat.presentation.theme.ui.LightGray
import com.flasska.petstat.presentation.theme.ui.LightGray2
import com.flasska.petstat.presentation.theme.ui.Orange
import com.flasska.petstat.presentation.theme.ui.LightBlack

@Composable
fun DeviceStatisticText(deviceName: String?) {
    Text(
        text = buildAnnotatedString {
            append("Статистика устройства ")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(deviceName ?: "Неизвестно")
            }
        },
        color = Black,
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
    )
}

@Composable
fun BackText(deviceName: String?, screenState: DeviceStatisticState,
             screenEvent: (DeviceStatisticEvent) -> Unit) {
    val batteryIcon = painterResource(id = R.drawable.ic_battery)
    val inlineContentId = "batteryIcon"
    val device = screenState.device

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Mint)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, end = 0.dp, bottom = 32.dp)
        ) {
            // Верхняя панель с кнопкой и надписью
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(LightGray2)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { screenEvent(DeviceStatisticEvent.NavigateBack) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 60.dp, end = 20.dp, bottom = 7.dp)
                            .height(90.dp)
                            .border(
                                width = 1.dp,
                                color = LightBlack,
                                shape = RoundedCornerShape(24.dp)
                            ),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gray
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            device?.let { nonNullDevice ->
                                Icon(
                                    painter = painterResource(id = device.avatar),
                                    contentDescription = "Аватар",
                                    modifier = Modifier.size(64.dp)
                                        .alpha(0.45f),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(color = Black2)) {
                                            append("${device.name}\n")
                                        }
                                        withStyle(style = SpanStyle(color = LightGray)) {
                                            append("Неактивен")
                                        }
                                    },
                                    color = GreenForTextButton,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        // Текст сверху
        Text(
            text = "Устройство ${deviceName} в данный момент не активно, попробуйте переподключиться",
            color = Black,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 230.dp, horizontal = 20.dp)
        )
        // Прогресс по центру
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun DeviceStatisticScreen(
    screenState: DeviceStatisticState,
    screenEvent: (DeviceStatisticEvent) -> Unit
) {
    val batteryIcon = painterResource(id = R.drawable.ic_battery)
    val inlineContentId = "batteryIcon"
    val device = screenState.device

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (screenState.device?.isActive == false) {
            BackText(device?.name, screenState, screenEvent)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Mint)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, bottom = 32.dp)
                ) {
                    // Верхняя панель с кнопкой и надписью
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                            .background(Platina)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { screenEvent(DeviceStatisticEvent.NavigateBack) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 60.dp, end = 20.dp, bottom = 7.dp)
                                    .height(90.dp)
                                    .border(
                                        width = 1.dp,
                                        color = GreenForBorder,
                                        shape = RoundedCornerShape(24.dp)
                                    ),
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    device?.let { nonNullDevice ->
                                        Icon(
                                            painter = painterResource(id = device.avatar),
                                            contentDescription = "Аватар",
                                            modifier = Modifier.size(64.dp),
                                            tint = Color.Unspecified
                                        )
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = Black)) {
                                                    append("${device.name}\n")
                                                }
                                                appendInlineContent("batteryIcon", "[icon]")
                                                append(" ${device.batteryLevel} %")
                                            },
                                            inlineContent = mapOf(
                                                "batteryIcon" to InlineTextContent(
                                                    placeholder = Placeholder(
                                                        width = 24.sp,
                                                        height = 24.sp,
                                                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                                                    )
                                                ) {
                                                    Icon(
                                                        painter = batteryIcon,
                                                        contentDescription = "Battery Icon",
                                                        tint = when {
                                                            device.batteryLevel < 30 -> Red
                                                            device.batteryLevel in 30..59 -> Yellow
                                                            else -> GreenForTextButton
                                                        }
                                                    )
                                                }
                                            ),
                                            color = when {
                                                device.batteryLevel < 30 -> Red
                                                device.batteryLevel in 30..59 -> Yellow
                                                else -> GreenForTextButton
                                            },
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(6.dp)
                                        )
                                    }
                                }
                            }
                            DeviceStatisticText(device?.name) // Текст с названием устройства
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Карточка-контейнер с заголовком и информацией по здоровью
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = GreenJungli),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Здоровье",
                                style = MaterialTheme.typography.headlineMedium.copy(color = White)
                            )

                            Spacer(modifier = Modifier.height(38.dp))

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(4) { index ->
                                    when (index) {
                                        0, 3 -> StepCard()
                                        1 -> HeartRateCard()
                                        2 -> SleepCard()
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun StepCard(){
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ){
        Box(modifier = Modifier.fillMaxSize()) {
            // Задний фон (изображение лап)
            Image(
                painter = painterResource(id = R.drawable.ic_steps),
                contentDescription = "Шаги фон",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Шагов", style = MaterialTheme.typography.titleMedium, fontSize = 20.sp)
                Spacer(Modifier.height(4.dp))
                Text("2,481 шагов", style = MaterialTheme.typography.bodySmall, fontSize = 14.sp) // Количество шагов
                Text("1.5 км", style = MaterialTheme.typography.bodySmall, fontSize = 14.sp) // Можно создать функцию, которая будет высчитывать по шагам расстояние

                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun HeartRateCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Пульс", style = MaterialTheme.typography.titleMedium, fontSize = 20.sp)
            Spacer(Modifier.height(4.dp))
            Text("74 уд/мин", style = MaterialTheme.typography.bodySmall, fontSize = 14.sp)

            Spacer(Modifier.weight(1f))

            LineChartHeartRate(color = Orange)
        }
    }
}

@Composable
fun SleepCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Сон", style = MaterialTheme.typography.titleMedium, fontSize = 20.sp)
            Spacer(Modifier.height(4.dp))
            Text("6 ч 7 мин", style = MaterialTheme.typography.bodySmall, fontSize = 14.sp)

            Spacer(Modifier.weight(1f))

            LineChartSleep(color = LightBlue)
        }
    }
}

@Composable
fun LineChartHeartRate(color: Color) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
    ) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.6f)
            lineTo(size.width * 0.45f, size.height * 0.1f)
            lineTo(size.width * 0.3f, size.height * 0.67f)
            lineTo(size.width * 0.72f, size.height * 0.1f)
            lineTo(size.width, size.height * 0.4f)
        }
        drawPath(path, color = color, style = Stroke(width = 2.dp.toPx()))
    }
}

@Composable
fun LineChartSleep(color: Color) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
    ) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.6f)
            lineTo(size.width * 0.25f, size.height * 0.2f)
            lineTo(size.width * 0.5f, size.height * 0.6f)
            lineTo(size.width * 0.75f, size.height * 0.3f)
            lineTo(size.width, size.height * 0.7f)
        }
        drawPath(path, color = color, style = Stroke(width = 2.dp.toPx()))
    }
}