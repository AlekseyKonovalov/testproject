package com.alekseykon.testproject.presentation.fragments.requisites

import com.alekseykon.testproject.presentation.fragments.base.BaseViewState

internal data class RequisitesViewState(
    val isLoading: Boolean = false,
    val orgName: String? = "",
    val isOrgNameValid: Boolean = false,
    val inn: String? = "",
    val isInnValid: Boolean = false,
    val bik: String? = "",
    val isBikValid: Boolean = false,
    val account: String? = "",
    val isAccountValid: Boolean = false,
    val errorText: String = ""
) : BaseViewState