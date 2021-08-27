package com.alekseykon.testproject.presentation.fragments.instructions

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class InstructionsAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : InstructionsAction()
}