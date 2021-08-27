package com.alekseykon.testproject.domain.interactors.files

import com.alekseykon.testproject.domain.repositories.files.FilesRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

internal class FilesInteractorImpl @Inject constructor(
    private val filesRepository: FilesRepository,
) : FilesInteractor {
    override suspend fun getFileByUrl(url: String, documentName: String): Flow<File> {
        return filesRepository.getFileByUrl(url, documentName)
    }
}