package com.alekseykon.testproject.domain.di.modules.registration

import com.alekseykon.testproject.domain.interactors.registration.RegistrationInteractor

internal interface RegistrationProvide {

    fun provideRegistrationInteractor(): RegistrationInteractor
}