package com.alekseykon.testproject.data.di.modules.services

import android.content.Context
import com.alekseykon.testproject.data.services.preference.PreferenceServiceImpl
import com.alekseykon.testproject.domain.services.preference.PreferenceService
import com.alekseykon.testproject.data.di.DataScope
import dagger.Module
import dagger.Provides

@Module
internal class ServicesModule {

    @DataScope
    @Provides
    fun providePreferenceService(context: Context): PreferenceService =
        PreferenceServiceImpl(context)

}