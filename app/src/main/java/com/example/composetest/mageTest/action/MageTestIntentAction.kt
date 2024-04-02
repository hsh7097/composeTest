package com.example.composetest.mageTest.action

import com.example.composetest.mage.action.IntentAction


sealed class MageTestIntentAction : IntentAction {
    object GoBack : MageTestIntentAction()
    data class LandingAction(val url: String) : MageTestIntentAction()
    data class UpdateIntData(val data: Int, val updateData: Int) : MageTestIntentAction()
}
