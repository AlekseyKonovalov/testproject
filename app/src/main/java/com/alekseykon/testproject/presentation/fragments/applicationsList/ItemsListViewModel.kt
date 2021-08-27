package com.alekseykon.testproject.presentation.fragments.applicationsList

import androidx.lifecycle.viewModelScope
import com.alekseykon.testproject.R
import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractor
import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ItemsListViewModel @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val registrationInteractor: RegistrationInteractor
) : BaseViewModel<ItemsListViewState, ItemsListAction>(ItemsListViewState()) {

    private var searchJob: Job? = null

    override fun onReduceState(viewAction: ItemsListAction) = when (viewAction) {
        is ItemsListAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow,
            errorText = ""
        )
        is ItemsListAction.ShowSearchLoading -> viewState.value.copy(
            isSearchLoading = viewAction.isShow,
            errorText = ""
        )
        is ItemsListAction.UpdateFields -> viewState.value.copy(
            searchPhone = viewAction.phone,
            errorText = ""
        )
        is ItemsListAction.ShowError -> viewState.value.copy(
            errorText = viewAction.errorText
        )
        is ItemsListAction.UpdateItems -> viewState.value.copy(
            searchItems = viewAction.items,
            errorText = ""
        )
    }

    fun onGoToPaymentClick(searchItemEntity: SearchItemEntity) {

    }

    fun searchApplicationsByLastPhone() {
        searchItems(viewState.value.searchPhone)
    }


    fun searchApplicationsByNewPhone(phone: String) {
        sendAction(ItemsListAction.UpdateFields(phone))
        searchItems(phone)
    }

    private fun searchItems(phone: String) {
        if (searchJob?.isActive == true) searchJob?.cancel()
        searchJob = viewModelScope.launch {
            sendAction(ItemsListAction.ShowSearchLoading(true))
            registrationInteractor.searchApplicationsByPhone(phone)
                .collect {
                    sendAction(ItemsListAction.ShowSearchLoading(false))
                    if (it.status == DataResult.Status.SUCCESS) {
                        sendAction(ItemsListAction.UpdateItems(it.data ?: listOf()))
                    } else {
                        sendAction(
                            ItemsListAction.ShowError(
                                it.error?.statusMessage ?: resourcesManager.getString(R.string.default_error)
                            )
                        )
                    }
                }

        }
    }

}