package com.alekseykon.testproject.presentation.fragments.pdf

import com.alekseykon.testproject.presentation.fragments.base.BaseViewState
import java.io.File

internal data class PdfViewViewState(
    val isLoading: Boolean = false,
    val documentFile: File? = null
) : BaseViewState