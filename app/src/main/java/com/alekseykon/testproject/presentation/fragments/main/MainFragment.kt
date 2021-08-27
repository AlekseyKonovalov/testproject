package com.alekseykon.testproject.presentation.fragments.main

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.launchWhenStarted
import com.alekseykon.testproject.databinding.FragmentMainBinding
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.onEach


internal class MainFragment : BaseFragment<FragmentMainBinding>(
    layoutRes = R.layout.fragment_main
) {

    val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return false
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: MainViewState) {
        if (state.isLoading) showProgress() else hideProgress()
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val hostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(hostFragment.navController)
    }

}