package com.alekseykon.testproject.presentation.di.utils

import com.alekseykon.testproject.presentation.di.PresentationScope
import com.alekseykon.testproject.presentation.navigation.NavManager
import com.alekseykon.testproject.presentation.navigation.NavManagerImpl
import dagger.Module
import dagger.Provides

@Module
internal class UtilsModule {

    @PresentationScope
    @Provides
    fun provideNavManager(): NavManager = NavManagerImpl()

}