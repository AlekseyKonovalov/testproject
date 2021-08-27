package com.alekseykon.testproject.presentation.fragments.pdf

import androidx.lifecycle.viewModelScope
import com.alekseykon.testproject.domain.interactors.files.FilesInteractor
import com.alekseykon.testproject.presentation.fragments.base.BaseViewModel
import com.alekseykon.testproject.presentation.utils.ResourcesManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PdfViewModel @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val filesInteractor: FilesInteractor
) : BaseViewModel<PdfViewViewState, PdfViewAction>(PdfViewViewState()) {

    override fun onReduceState(viewAction: PdfViewAction) = when (viewAction) {
        is PdfViewAction.SetupFile -> viewState.value.copy(
            documentFile = viewAction.documentFile
        )
        is PdfViewAction.ShowLoading -> viewState.value.copy(
            isLoading = viewAction.isShow
        )
    }

    fun init(documentUrl: String, documentName: String) {
        sendAction(PdfViewAction.ShowLoading(true))
        viewModelScope.launch {
            filesInteractor.getFileByUrl(documentUrl, documentName)
                .collect { file ->
                    sendAction(PdfViewAction.ShowLoading(false))
                    sendAction(PdfViewAction.SetupFile(file))
                }

        }
    }


}