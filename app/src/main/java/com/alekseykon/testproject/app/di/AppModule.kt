package com.alekseykon.testproject.app.di

import android.app.Application
import android.content.Context
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import com.alekseykon.testproject.presentation.utils.ResourcesManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class AppModule {

    @AppScope
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @AppScope
    @Provides
    @Named("filesDirPath")
    fun provideFilesDirPath(context: Context): String = context.filesDir.path


    @AppScope
    @Provides
    fun provideResourceManager(context: Context): ResourcesManager = ResourcesManagerImpl(context)
}