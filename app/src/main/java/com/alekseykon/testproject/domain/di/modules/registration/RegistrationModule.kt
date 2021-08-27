package com.alekseykon.testproject.domain.di.modules.registration

import com.alekseykon.testproject.domain.di.DomainScope
import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractor
import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractorImpl
import com.alekseykon.testproject.domain.repositories.registration.RegistrationRepository
import com.alekseykon.testproject.domain.services.preference.PreferenceService
import dagger.Module
import dagger.Provides


@Module
internal class RegistrationModule {

    @DomainScope
    @Provides
    fun provideRegistrationInteractor(
        registrationRepository: RegistrationRepository,
        preferenceService: PreferenceService
    ): RegistrationInteractor {
        return RegistrationInteractorImpl(registrationRepository, preferenceService)
    }

}