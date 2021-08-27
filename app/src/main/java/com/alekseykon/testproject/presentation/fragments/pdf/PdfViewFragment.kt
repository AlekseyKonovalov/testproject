package com.alekseykon.testproject.presentation.fragments.pdf

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.launchWhenStarted
import com.alekseykon.testproject.databinding.FragmentPdfViewBinding
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.flow.onEach

internal class PdfViewFragment : BaseFragment<FragmentPdfViewBinding>(
    layoutRes = R.layout.fragment_pdf_view
) {

    private val args: PdfViewFragmentArgs by navArgs()

    val viewModel: PdfViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: PdfViewViewState) {
        if (state.isLoading) showProgress() else hideProgress()
        state.documentFile?.let {
            binding.pdfView.fromFile(it)
                .spacing(SPACE_MARGIN)
                .load()
        }
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        viewModel.init(args.documentUrl, args.documentNameWithExtension)
        binding.toolbar.setNavigationOnClickListener {
            backPress()
        }
        binding.toolbar.title = args.documentTitle
    }

    override fun initBindingValues(binding: FragmentPdfViewBinding) {

    }

    companion object {
        const val PDF_FILE_TITLE = "PDF_FILE_TITLE"
        const val SPACE_MARGIN = 16
    }

}