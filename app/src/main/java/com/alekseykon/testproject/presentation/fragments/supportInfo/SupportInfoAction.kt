package com.alekseykon.testproject.presentation.fragments.supportInfo

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class SupportInfoAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : SupportInfoAction()
}