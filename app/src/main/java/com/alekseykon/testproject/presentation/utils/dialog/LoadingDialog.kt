package com.alekseykon.testproject.presentation.utils.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.alekseykon.testproject.R

internal class LoadingDialog : androidx.fragment.app.DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_loading, null)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(view)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = false
    }

    companion object {

        fun view(fm: FragmentManager, tag: String): LoadingView {
            return object : LoadingView {

                private var dialog: LoadingDialog? = null

                override fun showProgress() {
                    if (dialog != null) return
                    dialog = LoadingDialog().also {
                        fm.beginTransaction()
                            .add(it, tag)
                            .commitAllowingStateLoss()
                    }
                }

                override fun hideProgress() {
                    val dialog = (fm.findFragmentByTag(tag) as LoadingDialog?) ?: dialog
                    dialog?.dismissAllowingStateLoss()
                    this.dialog = null
                }
            }
        }

    }
}