package com.alekseykon.testproject.presentation.fragments.main

import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import javax.inject.Inject


internal class MainViewModel @Inject constructor(
) : BaseViewModel<MainViewState, MainAction>(MainViewState()) {

    override fun onReduceState(viewAction: MainAction) = when (viewAction) {
        is MainAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow
        )
    }

}