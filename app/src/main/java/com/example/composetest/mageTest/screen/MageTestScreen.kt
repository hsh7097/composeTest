package com.example.composetest.mageTest.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.example.composetest.mage.action.MageBaseIntentAction
import com.example.composetest.mage.action.OnIntentAction
import com.example.composetest.mage.screen.MageContent
import com.example.composetest.mage.screen.MageErrorView
import com.example.composetest.mage.screen.MageSuccessView
import com.example.composetest.mageTest.action.MageTestIntentAction
import com.example.composetest.mageTest.viewModel.MageTestViewModel

@Composable
fun MageTestScreen(
    viewModel: MageTestViewModel,
) {
    val state = viewModel.state.collectAsState()

    val intentAction: OnIntentAction = { intentAction ->
        viewModel.handleIntent(intentAction)
    }
    LaunchedEffect(true) {
        intentAction.invoke(MageBaseIntentAction.FetchData("테스트", true))
    }


    MageContent(
        uiState = state.value,
        successContentView = { items ->
            MageSuccessView(
                items = items,
                onIntentAction = intentAction
            )
        },
        errorContentView = { errorMessage ->
            MageErrorView(
                errorMessage = errorMessage,
                onIntentAction = intentAction
            )
        }
    )
}