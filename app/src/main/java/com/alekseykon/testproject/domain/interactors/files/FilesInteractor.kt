package com.alekseykon.testproject.domain.interactors.files

import kotlinx.coroutines.flow.Flow
import java.io.File

internal interface FilesInteractor {
    suspend fun getFileByUrl(url: String, documentName: String): Flow<File>
}