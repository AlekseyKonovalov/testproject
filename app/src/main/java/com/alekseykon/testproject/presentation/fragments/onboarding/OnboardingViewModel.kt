package com.alekseykon.testproject.presentation.fragments.onboarding

import com.alekseykon.testproject.R
import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.fragments.onboarding.model.OnboardingButtonItem
import com.alekseykon.testproject.presentation.fragments.onboarding.model.OnboardingImageItem
import com.alekseykon.testproject.presentation.navigation.NavManager
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import javax.inject.Inject

internal class OnboardingViewModel @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val navManager: NavManager
) : BaseViewModel<OnboardingViewState, OnboardingAction>(OnboardingViewState()) {

    val imageItems: List<OnboardingImageItem> = listOf(
        OnboardingImageItem(R.drawable.ic_baseline_local_fire_department_24, R.color.light_blue, R.string.instructions_onboarding1),
        OnboardingImageItem(R.drawable.ic_baseline_local_fire_department_24, R.color.light_pink, R.string.instructions_onboarding2),
        OnboardingImageItem(R.drawable.ic_baseline_local_fire_department_24, R.color.light_yellow, R.string.instructions_onboarding3),
        OnboardingImageItem(R.drawable.ic_baseline_local_fire_department_24, R.color.light_green, R.string.instructions_onboarding4),
        OnboardingImageItem(R.drawable.ic_baseline_local_fire_department_24, R.color.light_pink2, R.string.instructions_onboarding5)
    )

    var counterButtonItems: List<OnboardingButtonItem> = updateCounterButtonItems()
    private var currentIndex: Int = 0

    override fun onReduceState(viewAction: OnboardingAction) = when (viewAction) {
        is OnboardingAction.NextImage -> viewState.value.copy(
            counterButtonItems = counterButtonItems,
            currentIndex = currentIndex,
            nextBtnText = getNextBtnText()
        )
    }

    fun onCounterButtonClick(index: Int) {
        currentIndex = index
        counterButtonItems = updateCounterButtonItems()
        sendAction(OnboardingAction.NextImage)
    }

    fun onSkipBtnClick() {
        navigateToMainScreen()
    }

    fun onNextBtnClick() {
        if (currentIndex == imageItems.indices.last()) {
            navigateToMainScreen()
        } else {
            currentIndex += 1
            counterButtonItems = updateCounterButtonItems()
            sendAction(OnboardingAction.NextImage)
        }
    }

    private fun navigateToMainScreen() {
        navManager.navigate(OnboardingFragmentDirections.actionOnboardingFragmentToMainFragment())
    }

    private fun updateCounterButtonItems(): List<OnboardingButtonItem> {
        return mutableListOf<OnboardingButtonItem>().apply {
            for (i in imageItems.indices) {
                add(OnboardingButtonItem((i + 1).toString(), i == currentIndex))
            }
        }.toList()
    }

    private fun getNextBtnText(): String {
        return if (currentIndex == imageItems.indices.last()) {
            resourcesManager.getString(R.string.onboarding_ok_text)
        } else {
            resourcesManager.getString(R.string.onboarding_next_text)
        }
    }
}