package com.alekseykon.testproject.presentation.fragments.registration

import com.alekseykon.testproject.presentation.fragments.base.BaseAction


internal sealed class RegistrationAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : RegistrationAction()
    internal class EnableButton(val isEnabled: Boolean) : RegistrationAction()
    internal object UpdateOfferCheckbox : RegistrationAction()
    internal object UpdateTextFields : RegistrationAction()
    internal object FinishApp : RegistrationAction()
    internal class ShowError(val errorText: String) : RegistrationAction()
}