package com.alekseykon.testproject.presentation.fragments.onboarding

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.launchWhenStarted
import com.alekseykon.testproject.databinding.FragmentOnboardingBinding
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import com.alekseykon.testproject.presentation.fragments.onboarding.adapter.HorizontalMarginItemDecoration
import com.alekseykon.testproject.presentation.fragments.onboarding.adapter.OnboardingButtonsAdapter
import com.alekseykon.testproject.presentation.fragments.onboarding.adapter.OnboardingImagesAdapter
import kotlinx.coroutines.flow.onEach

internal class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(
    layoutRes = R.layout.fragment_onboarding
) {

    val viewModel: OnboardingViewModel by viewModels {
        viewModelFactory
    }

    private val buttonAdapter by lazy {
        OnboardingButtonsAdapter(
            viewModel.counterButtonItems.toMutableList(),
            viewModel::onCounterButtonClick
        )
    }

    private val imageAdapter by lazy {
        OnboardingImagesAdapter(
            viewModel.imageItems
        )
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: OnboardingViewState) {
        if (state.isLoading) showProgress() else hideProgress()
        buttonAdapter.setData(state.counterButtonItems)
        binding.imagesItems.currentItem = state.currentIndex
        binding.nextButton.text = state.nextBtnText
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        initImageViewPager()
        binding.itemsButtons.adapter = buttonAdapter
        binding.nextButton.setOnClickListener {
            viewModel.onNextBtnClick()
        }
        binding.skipBtn.setOnClickListener {
            viewModel.onSkipBtnClick()
        }
    }

    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return false
    }

    private fun initImageViewPager() {
        binding.imagesItems.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.imagesItems.adapter = imageAdapter

        binding.imagesItems.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onCounterButtonClick(position)
            }
        })
        setImageViewPagerDecoration()
    }

    private fun setImageViewPagerDecoration() {
        binding.imagesItems.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
        }
        binding.imagesItems.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.imagesItems.addItemDecoration(itemDecoration)
    }

}