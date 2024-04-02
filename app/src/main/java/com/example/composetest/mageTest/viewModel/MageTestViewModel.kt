package com.example.composetest.mageTest.viewModel

import com.example.composetest.mage.action.IntentAction
import com.example.composetest.mage.list.ComposeListItem
import com.example.composetest.mage.state.MageUIState
import com.example.composetest.mage.viewModel.MageComposeViewModel
import com.example.composetest.mageTest.action.MageTestIntentAction
import com.example.composetest.mageTest.manager.MageTestListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MageTestViewModel : MageComposeViewModel<String, List<Any>>() {

    override fun actionIntentConsumer(intentAction: IntentAction) {
        super.actionIntentConsumer(intentAction)
        when (intentAction) {
            is MageTestIntentAction -> onMageTestActionIntent(intentAction)
        }
    }

    private fun onMageTestActionIntent(intentAction: MageTestIntentAction) {
        when (intentAction) {
            MageTestIntentAction.GoBack -> {
            }

            is MageTestIntentAction.LandingAction -> {
            }

            is MageTestIntentAction.UpdateIntData -> {
                updateIntData(intentAction)
            }

        }
    }

    private fun updateIntData(updateIntData: MageTestIntentAction.UpdateIntData): Job = launch {
        (_state.value as? MageUIState.Success)?.also {
            val updateData = it.data.map { listItem ->
                when (listItem) {
                    is MageTestListItem.IntType -> {
                        if (listItem.data == updateIntData.data) {
                            listItem.copy(data = updateIntData.updateData)
                        } else {
                            listItem
                        }
                    }

                    else -> {
                        listItem
                    }
                }
            }
            _state.emit(MageUIState.Success(updateData))
        }
    }






    override suspend fun createList(response: List<Any>?): List<ComposeListItem<*>> {
        return MageTestListItem.createList(response)
    }

    override fun fetchData(request: String, forceRequest: Boolean): Job = launch {
        super.fetchData(request, forceRequest)
        //repository 데이터 호출
        arrayListOf<Any>().apply {
            for (intValue in 0..99) {
                add(intValue)
            }
        }.let {
            onFetchDataSuccess(request, it)
        }
    }

}
