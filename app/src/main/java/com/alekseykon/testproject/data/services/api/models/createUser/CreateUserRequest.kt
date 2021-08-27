package com.alekseykon.testproject.data.services.api.models.createUser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class CreateUserRequest(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("secondName")
    val secondName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
) : Parcelable