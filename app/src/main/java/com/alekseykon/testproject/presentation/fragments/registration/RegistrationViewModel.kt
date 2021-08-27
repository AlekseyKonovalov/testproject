package com.alekseykon.testproject.presentation.fragments.registration

import androidx.lifecycle.viewModelScope
import com.alekseykon.testproject.BuildConfig
import com.alekseykon.testproject.R
import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractor
import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.navigation.NavManager
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class RegistrationViewModel @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val registrationInteractor: RegistrationInteractor,
    private val navManager: NavManager
) : BaseViewModel<RegistrationViewState, RegistrationAction>(RegistrationViewState()) {

    private var firstName: String = ""
    private var isFirstNameValid: Boolean = false

    private var secondName: String = ""
    private var isSecondValid: Boolean = false

    private var phoneNumber: String = ""
    private var isPhoneNumberValid: Boolean = false

    private var isCheckedAgreement = false

    override fun onReduceState(viewAction: RegistrationAction) = when (viewAction) {
        is RegistrationAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow,
            errorText = ""
        )
        is RegistrationAction.UpdateTextFields -> viewState.value.copy(
            firstName = firstName,
            secondName = secondName,
            phoneNumber = phoneNumber,
            isFirstNameValid = isFirstNameValid,
            isSecondValid = isSecondValid,
            isPhoneNumberValid = isPhoneNumberValid,
            errorText = ""
        )
        is RegistrationAction.EnableButton -> viewState.value.copy(
            isBtnEnabled = viewAction.isEnabled,
            errorText = ""
        )
        is RegistrationAction.UpdateOfferCheckbox -> viewState.value.copy(
            isCheckedAgreement = isCheckedAgreement,
            errorText = ""
        )
        is RegistrationAction.FinishApp -> viewState.value.copy(
            isFinishApp = true,
            errorText = ""
        )
        is RegistrationAction.ShowError -> viewState.value.copy(
            errorText = viewAction.errorText
        )
    }

    fun onConfirmBtnClick() {
        createOrganization()
    }

    fun updateOfferAgreementState(isChecked: Boolean) {
        isCheckedAgreement = isChecked
        sendAction(RegistrationAction.UpdateOfferCheckbox)
        updateButtonState()
    }

    fun onClickOfferLink() {
        navManager.navigate(
            RegistrationFragmentDirections.actionRegistrationFragmentToPdfViewFragment(
                documentUrl = BuildConfig.OFFER_URL,
                documentNameWithExtension = resourcesManager.getString(R.string.offer_document_name),
                documentTitle = resourcesManager.getString(R.string.offer_title)
            )
        )
    }

    fun setInn(newInn: String, isValid: Boolean) {
        firstName = newInn
        isFirstNameValid = isValid
        sendAction(RegistrationAction.UpdateTextFields)
        updateButtonState()
    }

    fun setBik(newBik: String, isValid: Boolean) {
        secondName = newBik
        isSecondValid = isValid
        sendAction(RegistrationAction.UpdateTextFields)
        updateButtonState()
    }

    fun setAccount(newAccount: String, isValid: Boolean) {
        phoneNumber = newAccount
        isPhoneNumberValid = isValid
        sendAction(RegistrationAction.UpdateTextFields)
        updateButtonState()
    }

    private fun createOrganization() {
        sendAction(RegistrationAction.ShowLoading(true))
        viewModelScope.launch {
            registrationInteractor.createUser(firstName, secondName, phoneNumber)
                .collect {
                    if (it.status == DataResult.Status.SUCCESS) {
                        registrationInteractor.setCreateUserStatus()
                        navManager.navigate(RegistrationFragmentDirections.actionRegistrationFragmentToOnboardingFragment())
                    } else {
                        sendAction(RegistrationAction.ShowError(resourcesManager.getString(R.string.default_error)))
                    }
                }
        }
    }


    private fun updateButtonState() {
        val isAllTextFieldsValid = isPhoneNumberValid && isSecondValid && isFirstNameValid
        sendAction(RegistrationAction.EnableButton(isAllTextFieldsValid && isCheckedAgreement))
    }

}