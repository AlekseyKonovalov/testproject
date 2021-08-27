package com.alekseykon.testproject.presentation.fragments.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alekseykon.testproject.R
import com.alekseykon.testproject.core.extensions.bottom
import com.alekseykon.testproject.core.extensions.hideKeyboard
import com.alekseykon.testproject.core.extensions.setStatusBarLight
import com.alekseykon.testproject.core.extensions.top
import com.alekseykon.testproject.databinding.DialogErrorBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.alekseykon.testproject.presentation.di.main.MainModelFactory
import com.alekseykon.testproject.presentation.utils.dialog.LoadingDialog
import javax.inject.Inject


internal abstract class BaseFragment<viewBinding : ViewDataBinding>(
    @LayoutRes protected var layoutRes: Int
) : Fragment() {

    @Inject
    lateinit var viewModelFactory: MainModelFactory

    protected lateinit var binding: viewBinding

    protected open val dataBindingComponent: DataBindingComponent? = null
    protected open val isLightStatusBar: Boolean = true
    protected open val isInsetsPadding: Boolean = false

    private var topInset: Int = 0
    private var bottomInset: Int = 0

    private val loadingViewTag = "Progress-${this::class.java.simpleName}"
    private val loadingView by lazy { LoadingDialog.view(childFragmentManager, loadingViewTag) }

    protected val isLoadingObserver by lazy {
        Observer<Boolean> { isLoading ->
            if (isLoading) {
                loadingView.showProgress()
            } else {
                loadingView.hideProgress()
            }
        }
    }

    protected fun showProgress() {
        loadingView.showProgress()
    }

    protected fun hideProgress() {
        loadingView.hideProgress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeViewCreated()
        initBinding(inflater, container)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        initViews()
        requireActivity().setStatusBarLight(isLightStatusBar)
        setInsetsListener()
        setBackPress()
        setModelObservers()
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        initBindingValues(binding)
    }


    protected open fun beforeViewCreated() {}
    protected open fun initBindingValues(binding: viewBinding) {}
    protected open fun initViews() {}
    protected open fun setModelObservers() {}


    override fun onStop() {
        super.onStop()
        requireActivity().hideKeyboard()
    }

    protected open fun onBackPressed(): Boolean {
        return true
    }

    protected fun backPress() {
        if (!findNavController().navigateUp()) {
            requireActivity().finish()
        }
    }

    protected open fun onWindowInsets(top: Int, bottom: Int) {
        binding.root.updatePadding(top = top, bottom = bottom)
    }

    private fun setInsetsListener() {
        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            if (isInsetsPadding) {
                onWindowInsets(insets.top, insets.bottom)
            }
            insets
        }
    }

    private fun setBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (onBackPressed()) {
                backPress()
            }
        }
    }

    protected fun showSnackbar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(Color.BLACK)
            setTextColor(Color.WHITE)
            show()
        }
    }

    protected fun showErrorDialog(text: String) {
        val dialogBinding: DialogErrorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_error,
            null,
            false
        )
        val materialAlertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dialog_bg))
            .show()
        dialogBinding.descriptionText.text = if (text.isEmpty()) getString(R.string.default_error) else text
        dialogBinding.closeButton.setOnClickListener {
            materialAlertDialog?.dismiss()
        }
    }


}