package com.alekseykon.testproject.data.di.modules.repositories

import com.alekseykon.testproject.domain.repositories.files.FilesRepository
import com.alekseykon.testproject.domain.repositories.registration.RegistrationRepository

internal interface RepositoriesProvide {

    fun provideRegistrationRepository(): RegistrationRepository

    fun provideFilesRepository(): FilesRepository

}