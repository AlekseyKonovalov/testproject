package com.alekseykon.testproject.data.di.modules.repositories

import com.alekseykon.testproject.data.di.DataScope
import com.alekseykon.testproject.data.repositories.files.FilesRepositoryImpl
import com.alekseykon.testproject.data.repositories.registration.RegistrationRepositoryImpl
import com.alekseykon.testproject.data.services.api.ApiBaseService
import com.alekseykon.testproject.domain.repositories.files.FilesRepository
import com.alekseykon.testproject.domain.repositories.registration.RegistrationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class RepositoriesModule {

    @DataScope
    @Provides
    fun provideRegistrationRepository(
        apiBaseService: ApiBaseService
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(
            apiBaseService
        )
    }


    @DataScope
    @Provides
    fun provideFilesRepository(
        @Named("filesDirPath")
        filesDirPath: String
    ): FilesRepository {
        return FilesRepositoryImpl(filesDirPath)
    }

}