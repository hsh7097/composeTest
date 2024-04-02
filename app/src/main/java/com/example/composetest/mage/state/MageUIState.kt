package com.example.composetest.mage.state

import com.example.composetest.mage.list.ComposeListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class MageUIState {
    object Loading : MageUIState()

    //DataUpdate를 체크하여 수정된 데이터만 리스트 갱신 하도록 하기 위해 List를 MutableStateFlow 로 감싸도록 설정
    data class Success(val data: List<ComposeListItem<*>>) : MageUIState()

    data class Error(val message: String,val exception: Exception? = null) : MageUIState()
}