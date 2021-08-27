package com.alekseykon.testproject.data.retrofit

import com.google.gson.annotations.SerializedName

data class ErrorInfo(
    @SerializedName("error")
    val errorTitle: String,
    @SerializedName("error_description")
    val errorInfo: String
)
