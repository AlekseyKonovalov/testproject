package com.alekseykon.testproject.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class UserInfoEntity(
    val firstName: String,
    val secondName: String,
    val phoneNumber: String
) : Parcelable