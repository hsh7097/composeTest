package com.example.composetest.mageTest.screen.itemScreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.mage.action.MageBaseIntentAction
import com.example.composetest.mage.action.OnIntentAction

@Composable
fun MageTestItemStringScreen(
    itemId: String,
    data: String,
    onIntentAction: OnIntentAction
) {
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
                    text = "$data 테스트 ",
                    color = Color.White
                )
            }

            FilledTonalButton(
                modifier = Modifier
                    .width(80.dp),
                onClick = {
                    onIntentAction.invoke(MageBaseIntentAction.Refresh(false))
                }
            ) {
                Text(
                    text = "갱신",
                    color = Color.Red
                )
            }
        }

    }
}