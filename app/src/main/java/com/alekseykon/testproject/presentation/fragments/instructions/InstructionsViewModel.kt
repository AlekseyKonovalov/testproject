package com.alekseykon.testproject.presentation.fragments.instructions

import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.fragments.instructions.InstructionsAction
import com.alekseykon.testproject.presentation.fragments.instructions.InstructionsViewState
import javax.inject.Inject


internal class InstructionsViewModel @Inject constructor(
) : BaseViewModel<InstructionsViewState, InstructionsAction>(InstructionsViewState()) {

    override fun onReduceState(viewAction: InstructionsAction) = when (viewAction) {
        is InstructionsAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow
        )
    }

}