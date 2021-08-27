package com.alekseykon.testproject.presentation.fragments.splash

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class SplashAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : SplashAction()
}
