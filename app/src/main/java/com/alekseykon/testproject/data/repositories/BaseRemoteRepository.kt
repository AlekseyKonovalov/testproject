package com.alekseykon.testproject.data.repositories

import com.alekseykon.testproject.core.extensions.jsonToObject
import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.data.retrofit.ErrorInfo
import com.alekseykon.testproject.data.retrofit.ResultError
import retrofit2.Response

internal abstract class BaseRemoteRepository() {
    internal suspend fun <T, R> getResult(
        request: suspend () -> Response<T>,
        mapTo: (T?) -> R
    ): DataResult<R> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                val resultBody = result.body()
                DataResult.success(mapTo.invoke(resultBody))
            } else {
                val obj = result.errorBody()?.string()?.jsonToObject<ErrorInfo>()
                DataResult.error(ResultError(result.code(), obj?.errorInfo))

            }
        } catch (e: Throwable) {
            DataResult.error(ResultError(0, e.toString()))
        }
    }
}