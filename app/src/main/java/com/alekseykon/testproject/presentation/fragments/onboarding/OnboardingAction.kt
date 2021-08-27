package com.alekseykon.testproject.presentation.fragments.onboarding

import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class OnboardingAction : BaseAction {
    internal object NextImage : OnboardingAction()
}