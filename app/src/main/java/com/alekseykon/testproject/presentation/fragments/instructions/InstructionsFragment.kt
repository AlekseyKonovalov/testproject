package com.alekseykon.testproject.presentation.fragments.instructions

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.launchWhenStarted
import com.alekseykon.testproject.core.extensions.setOnDebouncedClickListener
import com.alekseykon.testproject.databinding.FragmentInstructionsBinding
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.onEach

internal class InstructionsFragment : BaseFragment<FragmentInstructionsBinding>(
    layoutRes = R.layout.fragment_instructions
) {

    val viewModel: InstructionsViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: InstructionsViewState) {
        if (state.isLoading) showProgress() else hideProgress()
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        binding.backButton.setOnDebouncedClickListener {
            backPress()
        }
    }

}