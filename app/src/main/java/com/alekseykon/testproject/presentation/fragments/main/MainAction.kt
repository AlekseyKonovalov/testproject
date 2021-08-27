package com.alekseykon.testproject.presentation.fragments.main

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class MainAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : MainAction()
}