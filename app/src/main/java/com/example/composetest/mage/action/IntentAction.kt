package com.example.composetest.mage.action


typealias OnIntentAction = (IntentAction) -> Unit

interface IntentAction

sealed class MageBaseIntentAction : IntentAction {
    data class FetchData<T>(val requestData: T, val forceRefresh: Boolean) : MageBaseIntentAction()
    data class Refresh(val forceRefresh: Boolean) : MageBaseIntentAction()
}

