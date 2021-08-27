package com.alekseykon.testproject.data.repositories.files

import com.alekseykon.testproject.domain.repositories.files.FilesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject
import javax.inject.Named

internal class FilesRepositoryImpl @Inject constructor(
    @Named("filesDirPath")
    private val filesDirPath: String
) : FilesRepository {
    override suspend fun getFileByUrl(url: String, documentName: String): Flow<File> {
        return flow {
            val fileName = filesDirPath + File.separator + documentName
            val outputFile = File(fileName)

            try {
                val url = URL(url)
                val urlConnection = url.openConnection()
                urlConnection.connect()

                if (outputFile.exists()) {
                    outputFile.delete()
                }
                outputFile.createNewFile()
                val outputStream = FileOutputStream(outputFile)

                val inputStream = BufferedInputStream(url.openStream(), 8192)
                val data = ByteArray(1024)
                var total = 0
                var count = inputStream.read(data)
                while (count != -1) {
                    total += count
                    outputStream.write(data, 0, count)
                    count = inputStream.read(data)
                }
                inputStream.close()
                outputStream.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            emit(outputFile)
        }.flowOn(Dispatchers.IO)
    }
}