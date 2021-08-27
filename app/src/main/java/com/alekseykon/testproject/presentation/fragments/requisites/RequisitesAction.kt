package com.alekseykon.testproject.presentation.fragments.requisites

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class RequisitesAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : RequisitesAction()
    internal object UpdateTextFields : RequisitesAction()
    internal class ShowError(val errorText: String) : RequisitesAction()
}