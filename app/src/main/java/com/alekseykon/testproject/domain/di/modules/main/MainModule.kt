package com.alekseykon.testproject.domain.di.modules.main

import com.alekseykon.testproject.domain.di.DomainScope
import com.alekseykon.testproject.domain.interactors.files.FilesInteractor
import com.alekseykon.testproject.domain.interactors.files.FilesInteractorImpl
import com.alekseykon.testproject.domain.repositories.files.FilesRepository
import dagger.Module
import dagger.Provides


@Module
internal class MainModule {

    @DomainScope
    @Provides
    fun provideFilesInteractor(
        filesRepository: FilesRepository
    ): FilesInteractor {
        return FilesInteractorImpl(filesRepository)
    }
}