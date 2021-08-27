package com.alekseykon.testproject.domain.repositories.files

import kotlinx.coroutines.flow.Flow
import java.io.File

internal interface FilesRepository {
    suspend fun getFileByUrl(url: String, documentName: String): Flow<File>
}