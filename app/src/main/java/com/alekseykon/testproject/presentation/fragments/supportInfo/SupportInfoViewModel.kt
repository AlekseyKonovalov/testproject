package com.alekseykon.testproject.presentation.fragments.supportInfo

import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.fragments.supportInfo.SupportInfoAction
import com.alekseykon.testproject.presentation.fragments.supportInfo.SupportInfoViewState
import javax.inject.Inject

internal class SupportInfoViewModel @Inject constructor(
) : BaseViewModel<SupportInfoViewState, SupportInfoAction>(SupportInfoViewState()) {

    override fun onReduceState(viewAction: SupportInfoAction) = when (viewAction) {
        is SupportInfoAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow
        )
    }

}