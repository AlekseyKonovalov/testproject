package com.alekseykon.testproject.app.di

import android.content.Context
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import javax.inject.Named

internal interface AppProvider {

    fun provideContext(): Context

    @Named("filesDirPath")
    fun provideFilesDirPath(): String

    fun provideResourceManager(): ResourcesManager
}