package com.example.composetest.mageTest.screen.itemScreen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.mage.action.OnIntentAction
import com.example.composetest.mageTest.action.MageTestIntentAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MageTestItemIntScreen(
    itemId: String,
    data: Int,
    onIntentAction: OnIntentAction
) {
    Log.e("iiiii","callComposable $data")

    var isUpdate by remember { mutableStateOf(false) }

    LaunchedEffect(itemId){
        isUpdate = true // 딜레이가 끝나면 로딩 상태를 false로 변경합니다.
        delay(1000) // 3초 동안 딜레이합니다.
        isUpdate = false // 딜레이가 끝나면 로딩 상태를 false로 변경합니다.
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Red
            )
    ) {
        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.teal_200))
                .padding(8.dp)
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = "$data",
                    color = Color.White
                )

                if (isUpdate) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "데이터 업데이트",
                        color = Color.White
                    )
                }

            }

            FilledTonalButton(
                modifier = Modifier
                    .width(100.dp),
                onClick = {
                    onIntentAction.invoke(MageTestIntentAction.UpdateIntData(data, data + 100))
                }
            ) {
                Text(
                    text = "값 증가",
                    color = Color.Red
                )
            }
        }
    }
}