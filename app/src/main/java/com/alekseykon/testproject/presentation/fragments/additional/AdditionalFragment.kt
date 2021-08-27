package com.alekseykon.testproject.presentation.fragments.additional

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alekseykon.testproject.BuildConfig
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.launchWhenStarted
import com.alekseykon.testproject.core.extensions.setOnDebouncedClickListener
import com.alekseykon.testproject.databinding.FragmentAdditionalBinding
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.onEach


internal class AdditionalFragment : BaseFragment<FragmentAdditionalBinding>(
    layoutRes = R.layout.fragment_additional
) {

    val viewModel: AdditionalViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: AdditionalViewState) {
        if (state.isLoading) showProgress() else hideProgress()
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        binding.requisites.setOnDebouncedClickListener {
            findNavController().navigate(AdditionalFragmentDirections.actionAdditionalFragmentToRequisitesFragment())
        }
        binding.instructions.setOnDebouncedClickListener {
            findNavController().navigate(AdditionalFragmentDirections.actionAdditionalFragmentToInstructionsFragment())
        }
        binding.support.setOnDebouncedClickListener {
            findNavController().navigate(AdditionalFragmentDirections.actionAdditionalFragmentToSupportInfoFragment2())
        }
        binding.textVersion.text =
            getString(R.string.version_pattern, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString())
    }

}