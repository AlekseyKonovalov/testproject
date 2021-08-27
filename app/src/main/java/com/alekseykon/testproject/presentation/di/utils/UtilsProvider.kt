package com.alekseykon.testproject.presentation.di.utils

import com.alekseykon.testproject.presentation.navigation.NavManager

internal interface UtilsProvider {

    fun provideNavManager(): NavManager

}