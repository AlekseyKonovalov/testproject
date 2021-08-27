package com.alekseykon.testproject.presentation.fragments.splash

import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractor
import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.navigation.NavManager
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor,
    private val navManager: NavManager
) : BaseViewModel<SplashViewState, SplashAction>(SplashViewState()) {

    override fun onReduceState(viewAction: SplashAction) = when (viewAction) {
        is SplashAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow
        )
    }

    fun init() {
        when {
            registrationInteractor.getCreateUserStatus() -> {
                navManager.navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
            else -> {
                navManager.navigate(SplashFragmentDirections.actionSplashFragmentToRegistrationFragment())
            }
        }
    }

}