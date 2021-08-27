package com.alekseykon.testproject.presentation.fragments.registration

import com.alekseykon.testproject.presentation.fragments.base.BaseViewState

internal data class RegistrationViewState(
    val isLoading: Boolean = false,
    val firstName: String? = "",
    val isFirstNameValid: Boolean = false,
    val secondName: String? = "",
    val isSecondValid: Boolean = false,
    val phoneNumber: String? = "",
    val isPhoneNumberValid: Boolean = false,
    val isCheckedAgreement: Boolean = false,
    val isBtnEnabled: Boolean = false,
    val isFinishApp: Boolean = false,
    val errorText: String = ""
) : BaseViewState