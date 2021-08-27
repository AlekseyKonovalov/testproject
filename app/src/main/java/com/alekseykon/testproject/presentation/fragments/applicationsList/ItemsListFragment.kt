package com.alekseykon.testproject.presentation.fragments.applicationsList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.*
import com.alekseykon.testproject.core.extensions.hide
import com.alekseykon.testproject.core.extensions.show
import com.alekseykon.testproject.databinding.FragmentApplicationsListBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.applicationsList.adapter.ApplicationItemAdapter
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import com.alekseykon.testproject.presentation.utils.TextFieldMask
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class ItemsListFragment : BaseFragment<FragmentApplicationsListBinding>(
    layoutRes = R.layout.fragment_applications_list
) {

    val viewModel: ItemsListViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        ApplicationItemAdapter(
            viewModel::onGoToPaymentClick
        )
    }
    private var inputJob: Job? = null

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchApplicationsByLastPhone()
    }

    private fun updateViewState(state: ItemsListViewState) {
        if (state.isLoading) showProgress() else hideProgress()
        updatePhoneField(state)
        adapter.setData(state.searchItems)
        if (state.errorText.isNotEmpty()) {
            showErrorDialog(state.errorText)
        }
        if (state.isSearchLoading) binding.progressBar.show() else binding.progressBar.hide()
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        binding.applicationsItemsView.adapter = adapter
        initEditTextView(
            textListener = { text: String, isValid: Boolean ->
                if (inputJob?.isActive == true) inputJob?.cancel()
                inputJob = lifecycleScope.launch {
                    delay(500L)
                    viewModel.searchApplicationsByNewPhone(text)
                }
            },
            mask = TextFieldMask.DEFAULT_PHONE_FORMAT,
            editText = binding.searchTextEdit
        )
        binding.searchTextEdit.setOnFocusChangeListener { _, hasFocus ->
            updateFocus(hasFocus, binding.searchTextInput)
        }
        binding.searchIcon.setOnDebouncedClickListener {
            binding.searchTextEdit.setText("")
        }
        binding.updateButton.setOnDebouncedClickListener {
            viewModel.searchApplicationsByLastPhone()
        }
    }

    private fun initEditTextView(
        textListener: ((String, Boolean) -> Unit),
        mask: String = "",
        editText: TextInputEditText
    ) {
        val formatter = MaskedTextChangedListener(
            primaryFormat = mask,
            autocomplete = false,
            field = editText,
            listener = null,
            valueListener = null,
            rightToLeft = false
        )
        formatter.valueListener = object : MaskedTextChangedListener.ValueListener {
            var lastText = ""
            override fun onTextChanged(
                maskFilled: Boolean,
                extractedValue: String,
                formattedValue: String
            ) {
                if (lastText != formattedValue) {
                    lastText = formattedValue
                    textListener.invoke(extractedValue, maskFilled)
                }
            }
        }
        editText.addTextChangedListener(formatter)
        editText.onFocusChangeListener = formatter

    }

    private fun updatePhoneField(state: ItemsListViewState) {
        binding.searchTextEdit.setNewText(state.searchPhone)
        updateIcon(state.searchPhone)
    }

    private fun updateFocus(hasFocus: Boolean, inputText: TextInputLayout) {
        if (hasFocus) {
            inputText.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.shape_light_gray_accent_stroke)
        } else {
            inputText.background = AppCompatResources.getDrawable(requireContext(), R.drawable.shape_light_gray)
        }
    }

    private fun updateIcon(text: String) {
        val icon = if (text.isEmpty()) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        }
        binding.searchIcon.setImageDrawable(icon)
    }

}