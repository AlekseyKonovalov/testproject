package com.alekseykon.testproject.presentation.fragments.onboarding

import com.alekseykon.testproject.presentation.fragments.base.BaseViewState
import com.alekseykon.testproject.presentation.fragments.onboarding.model.OnboardingButtonItem

internal data class OnboardingViewState(
    val isLoading: Boolean = false,
    val currentIndex: Int = 0,
    val counterButtonItems: List<OnboardingButtonItem> = listOf(),
    val nextBtnText: String = ""
) : BaseViewState