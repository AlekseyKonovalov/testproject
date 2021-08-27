package com.alekseykon.testproject.presentation.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alekseykon.testproject.presentation.di.ViewModelKey
import com.alekseykon.testproject.presentation.fragments.additional.AdditionalViewModel
import com.alekseykon.testproject.presentation.fragments.applicationsList.ItemsListViewModel
import com.alekseykon.testproject.presentation.fragments.instructions.InstructionsViewModel
import com.alekseykon.testproject.presentation.fragments.main.MainViewModel
import com.alekseykon.testproject.presentation.fragments.onboarding.OnboardingViewModel
import com.alekseykon.testproject.presentation.fragments.pdf.PdfViewModel
import com.alekseykon.testproject.presentation.fragments.registration.RegistrationViewModel
import com.alekseykon.testproject.presentation.fragments.requisites.RequisitesViewModel
import com.alekseykon.testproject.presentation.fragments.splash.SplashViewModel
import com.alekseykon.testproject.presentation.fragments.supportInfo.SupportInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
internal abstract class MainModule {
    companion object {
        const val MAP_MODELS = "MAP_MODELS_MAIN"
    }

    @Binds
    abstract fun bindViewModelFactory(factory: MainModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(OnboardingViewModel::class)
    abstract fun bindOnboardingViewModel(viewModel: OnboardingViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(ItemsListViewModel::class)
    abstract fun bindRequestListViewModel(viewModel: ItemsListViewModel): ViewModel



    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(PdfViewModel::class)
    abstract fun bindPdfViewModel(viewModel: PdfViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(SupportInfoViewModel::class)
    abstract fun bindSupportInfoViewModel(viewModel: SupportInfoViewModel): ViewModel



    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(AdditionalViewModel::class)
    abstract fun bindAdditionalViewModel(viewModel: AdditionalViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(MainViewModel::class)
    abstract fun bindErrorMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(InstructionsViewModel::class)
    abstract fun bindInstructionsViewModel(viewModel: InstructionsViewModel): ViewModel

    @Binds
    @IntoMap
    @Named(MAP_MODELS)
    @ViewModelKey(RequisitesViewModel::class)
    abstract fun bindRequisitesViewModel(viewModel: RequisitesViewModel): ViewModel



}