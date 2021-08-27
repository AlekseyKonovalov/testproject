package com.alekseykon.testproject.presentation.fragments.pdf

import com.alekseykon.testproject.presentation.fragments.base.BaseAction
import java.io.File

internal sealed class PdfViewAction : BaseAction {
    internal class SetupFile(val documentFile: File) : PdfViewAction()
    internal class ShowLoading(val isShow: Boolean) : PdfViewAction()
}