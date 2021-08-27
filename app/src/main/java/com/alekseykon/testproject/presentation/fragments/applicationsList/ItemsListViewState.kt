package com.alekseykon.testproject.presentation.fragments.applicationsList

import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.presentation.fragments.base.BaseViewState

internal data class ItemsListViewState(
    val isLoading: Boolean = false,
    val searchPhone: String = "",
    val errorText: String = "",
    val searchItems: List<SearchItemEntity> = listOf(),
    val isSearchLoading: Boolean = false
) : BaseViewState