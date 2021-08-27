package com.alekseykon.testproject.presentation.di.main

import com.alekseykon.testproject.presentation.fragments.additional.AdditionalFragment
import com.alekseykon.testproject.presentation.fragments.applicationsList.ItemsListFragment
import com.alekseykon.testproject.presentation.fragments.instructions.InstructionsFragment
import com.alekseykon.testproject.presentation.fragments.main.MainFragment
import com.alekseykon.testproject.presentation.fragments.onboarding.OnboardingFragment
import com.alekseykon.testproject.presentation.fragments.pdf.PdfViewFragment
import com.alekseykon.testproject.presentation.fragments.registration.RegistrationFragment
import com.alekseykon.testproject.presentation.fragments.requisites.RequisitesFragment
import com.alekseykon.testproject.presentation.fragments.splash.SplashFragment
import com.alekseykon.testproject.presentation.fragments.supportInfo.SupportInfoFragment

internal interface MainInject {


    fun inject(fragment: OnboardingFragment)

    fun inject(fragment: RegistrationFragment)

    fun inject(fragment: ItemsListFragment)

    fun inject(fragment: SplashFragment)

    fun inject(fragment: PdfViewFragment)

    fun inject(fragment: SupportInfoFragment)

    fun inject(fragment: MainFragment)

    fun inject(fragment: AdditionalFragment)

    fun inject(fragment: RequisitesFragment)

    fun inject(fragment: InstructionsFragment)

}