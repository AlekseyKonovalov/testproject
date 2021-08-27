package com.alekseykon.testproject.core.extensions

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager


internal fun Activity.showKeyboard(view: View? = null) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.decorView.windowInsetsController?.show(WindowInsets.Type.ime())
    } else {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            if (view == null) {
                toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            } else {
                view.apply {
                    isFocusable = true
                    isFocusableInTouchMode = true
                }.requestFocus()

                toggleSoftInput(
                    InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }
        }
    }
}

internal fun Activity.hideKeyboard(view: View? = null) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.decorView.windowInsetsController?.hide(WindowInsets.Type.ime())
    } else {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            (view ?: currentFocus)?.let { v ->
                v.clearFocus()
                hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }
}