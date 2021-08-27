package com.alekseykon.testproject.presentation.fragments.supportInfo

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.launchWhenStarted
import com.alekseykon.testproject.core.extensions.setOnDebouncedClickListener
import com.alekseykon.testproject.databinding.FragmentSupportInfoBinding
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.onEach

internal class SupportInfoFragment : BaseFragment<FragmentSupportInfoBinding>(
    layoutRes = R.layout.fragment_support_info
) {

    val viewModel: SupportInfoViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: SupportInfoViewState) {
        if (state.isLoading) showProgress() else hideProgress()
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        binding.finishButton.setOnDebouncedClickListener {
            backPress()
        }
    }

}