package com.example.composetest.mageTest.manager

import androidx.compose.runtime.Composable
import com.example.composetest.mage.action.OnIntentAction
import com.example.composetest.mage.list.ComposeListItem
import com.example.composetest.mageTest.screen.itemScreen.MageTestItemIntScreen
import com.example.composetest.mageTest.screen.itemScreen.MageTestItemStringScreen


sealed class MageTestListItem<T : Any>(
    override val itemId: String = ComposeListItem.getItemUniqueId()
) : ComposeListItem<T> {
    companion object {
        fun createList(response: List<Any>?): List<ComposeListItem<*>> {
            return arrayListOf<MageTestListItem<*>>().apply {
                response?.forEach {
                    when (it) {
                        is String -> {
                            add(StringType(data = it))
                        }
                        is Int -> {
                            add(IntType(data = it))
                        }
                    }
                }
            }
        }
    }

    data class StringType(
        override val data: String,
        override val screen: @Composable (String, String, OnIntentAction) -> Unit = { itemId, itemData, onIntentAction ->
            MageTestItemStringScreen(itemId, itemData, onIntentAction)
        }
    ) : MageTestListItem<String>()

    data class IntType(
        override val data: Int,
        override val screen: @Composable (String, Int, OnIntentAction) -> Unit = { itemId, itemData, onIntentAction ->
            MageTestItemIntScreen(itemId, itemData, onIntentAction)
        }
    ) : MageTestListItem<Int>()
}
