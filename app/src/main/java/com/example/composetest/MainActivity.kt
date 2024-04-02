package com.example.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composetest.mageTest.screen.MageTestScreen
import com.example.composetest.mageTest.viewModel.MageTestViewModel
import com.example.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(innerPadding)
                    ) {
                        MageTestScreen(MageTestViewModel())
                    }
                }
            }
        }
    }
}
