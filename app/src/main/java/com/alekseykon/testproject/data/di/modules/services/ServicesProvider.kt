package com.alekseykon.testproject.data.di.modules.services


import com.alekseykon.testproject.domain.services.preference.PreferenceService


internal interface ServicesProvider {

    fun providePreferenceService(): PreferenceService

}