package com.example.composetest.mage.list

import androidx.compose.runtime.Composable
import com.example.composetest.mage.action.OnIntentAction

/**
 * 각 리스트 아이템의 공통 인터페이스 정의
 */
interface ComposeListItem<T : Any> {
    val itemId: String// 고유한 식별자
    val data: T// 데이터
    val screen: @Composable (String, T, OnIntentAction) -> Unit// 아이템의 UI 표현을 위한 컴포저블

    // 데이터와 함께 UI를 구성할 수 있는 함수
    @Composable
    fun ComposeScreenFromData(onIntentAction: OnIntentAction) = screen(itemId, data, onIntentAction)


    companion object {

        /**
         * 아이템의 고유한 ID를 생성
         */
        fun getItemUniqueId() = java.util.UUID.randomUUID().toString()
    }
}

