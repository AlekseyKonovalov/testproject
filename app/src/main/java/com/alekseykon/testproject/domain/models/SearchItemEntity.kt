package com.alekseykon.testproject.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
internal data class SearchItemEntity(
    val applicationId: String,
    val clientPhone: String,
    val applicationState: ApplicationStatus,
    val purchaseAmount: BigDecimal
) : Parcelable