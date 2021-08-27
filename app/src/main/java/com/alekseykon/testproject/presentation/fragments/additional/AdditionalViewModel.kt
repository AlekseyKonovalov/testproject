package com.alekseykon.testproject.presentation.fragments.additional

import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import javax.inject.Inject

internal class AdditionalViewModel @Inject constructor(
) : BaseViewModel<AdditionalViewState, AdditionalAction>(AdditionalViewState()) {

    override fun onReduceState(viewAction: AdditionalAction) = when (viewAction) {
        is AdditionalAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow
        )
    }

}