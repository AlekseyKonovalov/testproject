package com.alekseykon.testproject.presentation.fragments.requisites

import androidx.lifecycle.viewModelScope
import com.alekseykon.testproject.R
import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractor
import com.alekseykon.testproject.domain.models.UserInfoEntity
import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RequisitesViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor,
    private val resourcesManager: ResourcesManager
) : BaseViewModel<RequisitesViewState, RequisitesAction>(RequisitesViewState()) {

    private var firstName: String = ""
    private var isInnValid: Boolean = true

    private var secondName: String = ""
    private var isBikValid: Boolean = true

    private var phoneNumber: String = ""
    private var isAccountValid: Boolean = true

    override fun onReduceState(viewAction: RequisitesAction) = when (viewAction) {
        is RequisitesAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow,
            errorText = ""
        )
        is RequisitesAction.UpdateTextFields -> viewState.value.copy(
            inn = firstName,
            bik = secondName,
            account = phoneNumber,
            isInnValid = isInnValid,
            isBikValid = isBikValid,
            isAccountValid = isAccountValid,
            errorText = ""
        )
        is RequisitesAction.ShowError -> viewState.value.copy(
            errorText = viewAction.errorText
        )
    }

    fun getOrganization() {
        if (firstName.isNotEmpty() || secondName.isNotEmpty() || phoneNumber.isNotEmpty()) return
        sendAction(RequisitesAction.ShowLoading(true))
        viewModelScope.launch {
            registrationInteractor.getUser()
                .collect {
                    sendAction(RequisitesAction.ShowLoading(false))
                    if (it.status == DataResult.Status.SUCCESS) {
                        firstName = it.data?.firstName ?: ""
                        secondName = it.data?.secondName ?: ""
                        phoneNumber = it.data?.phoneNumber ?: ""
                        sendAction(RequisitesAction.UpdateTextFields)
                    } else {
                        sendAction(
                            RequisitesAction.ShowError(resourcesManager.getString(R.string.default_error))
                        )
                    }
                }
        }
    }

    fun updateOrganization() {
        if (!(isInnValid && isBikValid && isAccountValid)) return
        sendAction(RequisitesAction.ShowLoading(true))
        viewModelScope.launch {
            registrationInteractor.updateUserInfo(UserInfoEntity(firstName, secondName, phoneNumber))
                .collect {
                    sendAction(RequisitesAction.ShowLoading(false))
                    if (it.status == DataResult.Status.SUCCESS) {

                    } else {
                        sendAction(
                            RequisitesAction.ShowError(resourcesManager.getString(R.string.default_error))
                        )
                    }
                }
        }
    }

    fun setInn(newInn: String, isValid: Boolean) {
        firstName = newInn
        isInnValid = isValid
        sendAction(RequisitesAction.UpdateTextFields)
    }

    fun setBik(newBik: String, isValid: Boolean) {
        secondName = newBik
        isBikValid = isValid
        sendAction(RequisitesAction.UpdateTextFields)
    }

    fun setAccount(newAccount: String, isValid: Boolean) {
        phoneNumber = newAccount
        isAccountValid = isValid
        sendAction(RequisitesAction.UpdateTextFields)
    }

}