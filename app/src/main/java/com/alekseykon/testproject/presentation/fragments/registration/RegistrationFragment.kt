package com.alekseykon.testproject.presentation.fragments.registration

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.*
import com.alekseykon.testproject.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.fragments.base.BaseFragment
import com.alekseykon.testproject.presentation.utils.TextFieldMask.ACCOUNT_MASK
import com.alekseykon.testproject.presentation.utils.TextFieldMask.BIK_MASK
import com.alekseykon.testproject.presentation.utils.TextFieldMask.INN_MASK
import com.alekseykon.testproject.presentation.utils.TextFieldMask.IP_INN_MASK
import kotlinx.coroutines.flow.onEach

internal class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(
    layoutRes = R.layout.fragment_registration
) {

    val viewModel: RegistrationViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        PresentationComponent.get().inject(this)
        super.onAttach(context)
    }

    private fun updateViewState(state: RegistrationViewState) {
        if (state.isLoading) showProgress() else hideProgress()
        updateTextFields(state)
        binding.checkboxAgreement.setNewChecked(state.isCheckedAgreement)
        binding.confirmButton.isEnabled = state.isBtnEnabled
        if (state.isFinishApp) {
            requireActivity().finish()
        }
        if (state.errorText.isNotEmpty()) {
            showErrorDialog(state.errorText)
        }
    }

    override fun setModelObservers() {
        viewModel.viewState.onEach { state -> updateViewState(state) }
            .launchWhenStarted(lifecycleScope)
    }

    override fun initViews() {
        binding.checkboxAgreementText.setClickableText(
            clickableTextFragment = getString(R.string.registration_offer_agreement_clickable),
            useUnderline = false,
            onClickAction = {
                viewModel.onClickOfferLink()
            }
        )
        initView(
            textListener = { text: String, isValid: Boolean ->
                viewModel.setInn(text, isValid)
            },
            mask = INN_MASK,
            binding.innTextEdit,
            binding.innTextInput,
            affineFormats = listOf(IP_INN_MASK)
        )
        binding.innTextEdit.setOnFocusChangeListener { _, hasFocus ->
            updateFocus(hasFocus, binding.innTextInput)
        }
        initView(
            textListener = { text: String, isValid: Boolean ->
                viewModel.setBik(text, isValid)
            },
            mask = BIK_MASK,
            binding.bikTextEdit,
            binding.bikTextInput
        )
        binding.bikTextEdit.setOnFocusChangeListener { _, hasFocus ->
            updateFocus(hasFocus, binding.bikTextInput)
        }
        initView(
            textListener = { text: String, isValid: Boolean ->
                viewModel.setAccount(text, isValid)
            },
            mask = ACCOUNT_MASK,
            binding.accountTextEdit,
            binding.accountTextInput
        )
        binding.accountTextEdit.setOnFocusChangeListener { _, hasFocus ->
            updateFocus(hasFocus, binding.accountTextInput)
        }
        binding.checkboxAgreement.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateOfferAgreementState(isChecked)
        }
        binding.confirmButton.setOnDebouncedClickListener {
            viewModel.onConfirmBtnClick()
        }
    }


    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return false
    }

    private fun updateTextFields(state: RegistrationViewState) {
        binding.innTextEdit.setNewText(state.firstName)
        binding.bikTextEdit.setNewText(state.secondName)
        binding.accountTextEdit.setNewText(state.phoneNumber)
    }

    private fun initView(
        textListener: ((String, Boolean) -> Unit),
        mask: String = "",
        editText: TextInputEditText,
        inputText: TextInputLayout,
        affineFormats: List<String> = emptyList()
    ) {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_success_done)?.let { setIcon(it, inputText) }
        hideIcon(inputText)
        val formatter = MaskedTextChangedListener(
            primaryFormat = mask,
            affineFormats = affineFormats,
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
                    if (maskFilled) showIcon(inputText) else hideIcon(inputText)
                    textListener.invoke(extractedValue, maskFilled)
                }
            }
        }
        editText.addTextChangedListener(formatter)
        editText.onFocusChangeListener = formatter

    }

    private fun setIcon(drawable: Drawable, inputText: TextInputLayout) {
        inputText.endIconDrawable = drawable
        inputText.setEndIconTintList(ContextCompat.getColorStateList(requireContext(), R.color.forest_green))
    }

    private fun showIcon(inputText: TextInputLayout) {
        inputText.isEndIconVisible = true
    }

    private fun hideIcon(inputText: TextInputLayout) {
        inputText.isEndIconVisible = false
    }

    private fun updateFocus(hasFocus: Boolean, inputText: TextInputLayout) {
        if (hasFocus) {
            inputText.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.shape_light_gray_accent_stroke)
        } else {
            inputText.background = AppCompatResources.getDrawable(requireContext(), R.drawable.shape_light_gray)
        }
    }


}