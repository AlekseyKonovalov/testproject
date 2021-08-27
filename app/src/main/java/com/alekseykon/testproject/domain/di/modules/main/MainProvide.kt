package com.alekseykon.testproject.domain.di.modules.main

import com.alekseykon.testproject.domain.interactors.files.FilesInteractor

internal interface MainProvide {

    fun provideFilesInteractor(): FilesInteractor

}