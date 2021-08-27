package com.alekseykon.testproject.presentation.fragments.onboarding.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class OnboardingImageItem(
    @DrawableRes val imageRes: Int,
    @ColorRes val colorRes: Int,
    @StringRes val textRes: Int
)