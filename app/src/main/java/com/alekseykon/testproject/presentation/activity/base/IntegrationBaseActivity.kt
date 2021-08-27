package com.alekseykon.testproject.presentation.activity.base

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alekseykon.testproject.core.extensions.*
import com.alekseykon.testproject.core.extensions.getGlobalRect
import com.alekseykon.testproject.core.extensions.hideKeyboard
import com.alekseykon.testproject.core.extensions.setFullscreen
import com.alekseykon.testproject.core.extensions.setStatusBarLight


abstract class IntegrationBaseActivity : AppCompatActivity() {

    companion object {
        const val KEYBOARD_FACTOR = 0.15
    }

    abstract val resIdLayout: Int

    protected open val isFullScreen: Boolean = false
    protected open val isLightStatusBar: Boolean = false
    protected open val isInsetsPadding: Boolean = false
    protected open var isKeyboardAutoHide: Boolean = true

    private var viewDataBinding: ViewDataBinding? = null
    private var isKeyboardVisible = false

    private val keyboardVisibilityListener = ViewTreeObserver.OnGlobalLayoutListener {
        val rectangle = Rect().apply(window.decorView::getWindowVisibleDisplayFrame)
        val screenHeight = window.decorView.height
        val keypadHeight = screenHeight - rectangle.bottom
        val isVisible = keypadHeight > screenHeight * KEYBOARD_FACTOR

        if (isKeyboardVisible != isVisible) {
            onKeyboardVisibility(isVisible)
        }

        isKeyboardVisible = isVisible
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ViewDataBinding>(this, resIdLayout).also {
            viewDataBinding = it
        }
        setStatusBarLight(isLightStatusBar)
        setFullscreen(isFullScreen)
        setInsetsListener()
    }

    override fun onDestroy() {
        viewDataBinding = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        window.decorView.viewTreeObserver
            .addOnGlobalLayoutListener(keyboardVisibilityListener)
    }

    override fun onPause() {
        super.onPause()
        window.decorView.viewTreeObserver
            .removeOnGlobalLayoutListener(keyboardVisibilityListener)
    }


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            onEditTextFocus(event)
        }
        return super.dispatchTouchEvent(event)
    }

    protected open fun onKeyboardVisibility(isVisible: Boolean) {
        // Stub
    }

    private fun onEditTextFocus(event: MotionEvent) {
        (currentFocus as? EditText)?.let { view ->
            view.getGlobalRect().apply {
                if (!contains(event.rawX.toInt(), event.rawY.toInt())) {
                    onEditTextTouchOutside()
                }
            }
        }
    }

    private fun onEditTextTouchOutside() {
        if (isKeyboardAutoHide) {
            hideKeyboard()
        }
    }

    private fun setInsetsListener() {
        if (isInsetsPadding) {
            viewDataBinding?.root?.apply {
                setOnApplyWindowInsetsListener { view, insets ->
                    updatePadding(top = insets.top, bottom = insets.bottom)
                    insets
                }
            }
        }
    }
}