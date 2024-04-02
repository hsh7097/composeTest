package com.example.composetest.mage.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest.R
import com.example.composetest.mage.action.MageBaseIntentAction
import com.example.composetest.mage.action.OnIntentAction
import com.example.composetest.mage.list.ComposeListItem
import com.example.composetest.mage.state.MageUIState
import com.example.composetest.ui.theme.ComposeTestTheme


@Composable
fun MageContent(
    uiState: MageUIState,
    successContentView: @Composable ((List<ComposeListItem<*>>) -> Unit),
    loadingContentView: @Composable (() -> Unit)? = null,
    errorContentView: @Composable ((String) -> Unit)? = null
) {

    val lastSuccessData = remember {
        mutableStateOf<List<ComposeListItem<*>>>(emptyList())
    }
    when (uiState) {
        MageUIState.Loading -> {
            loadingContentView?.invoke() ?: successContentView(lastSuccessData.value)
        }

        is MageUIState.Success -> {
//            lastSuccessData.value = uiState.data
            lastSuccessData.value = uiState.data
            successContentView(lastSuccessData.value)
        }

        is MageUIState.Error -> {
            errorContentView?.invoke(uiState.message) ?: successContentView(lastSuccessData.value)
        }
    }
}

@Composable
fun MageSuccessView(
    items: List<ComposeListItem<*>>,
    content: @Composable ((List<ComposeListItem<*>>, OnIntentAction) -> Unit)? = null,
    onIntentAction: OnIntentAction
) {
    if (content != null) {
        content(items, onIntentAction)
    } else {
        DefaultSuccessView(
            items = items,
            onIntentAction = onIntentAction
        )
    }
}

@Composable
fun DefaultSuccessView(
    items: List<ComposeListItem<*>>,
    onIntentAction: OnIntentAction
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        items(items, key = {item -> item.itemId}) { item ->
            item.ComposeScreenFromData(onIntentAction)
        }
    }
}

@Composable
fun MageErrorView(
    errorMessage: String,
    content: @Composable ((String, OnIntentAction) -> Unit)? = null,
    onIntentAction: OnIntentAction
) {
    if (content != null) {
        content(errorMessage, onIntentAction)
    } else {
        DefaultErrorView(
            errorMessage = errorMessage,
            onIntentAction = onIntentAction
        )
    }
}


@Composable
fun DefaultErrorView(
    errorMessage: String,
    onIntentAction: OnIntentAction
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "로띠 로딩 이미지 위치"
        )
        Text(
            text = errorMessage,
            color = Color(0xFF222222),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 24.dp)
        )
        Text(
            text = "잠시 후 다시 시도해주세요.",
            color = Color(0xFF616161),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        RetryButton(
            modifier = Modifier.padding(top = 16.dp),
            onIntentAction = onIntentAction
        )
    }
}


@Composable
fun RetryButton(
    modifier: Modifier = Modifier,
    onIntentAction: OnIntentAction
) {
    Button(
        onClick = {
            onIntentAction.invoke(MageBaseIntentAction.Refresh(true))
        },
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "Refresh",
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "잠시 후 다시 시도해주세요.",
                color = Color(0xFF222222),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 3.5.dp)
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DefaultErrorViewPreview() {
    ComposeTestTheme {
        DefaultErrorView("오류 메세지") {}
    }
}


