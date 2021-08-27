package com.alekseykon.testproject.presentation.fragments.additional

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class AdditionalAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : AdditionalAction()
}