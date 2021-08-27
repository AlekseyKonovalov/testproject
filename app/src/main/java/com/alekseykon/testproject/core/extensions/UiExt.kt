package com.alekseykon.testproject.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.SystemClock
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.Executor

internal val WindowInsets.top: Int
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getInsets(WindowInsets.Type.statusBars()).top
    } else {
        systemWindowInsetTop
    }

internal val WindowInsets.bottom: Int
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getInsets(WindowInsets.Type.navigationBars()).bottom
    } else {
        systemWindowInsetBottom
    }

internal fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

internal fun Activity.setNavigationBarColor(color: Int) {
    window.navigationBarColor = color
}

@SuppressWarnings("deprecation")
internal fun Activity.setStatusBarLight(isLight: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.decorView.windowInsetsController?.apply {
            val appearance = if (isLight) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            setSystemBarsAppearance(appearance, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }
    } else {
        window.decorView.apply {
            systemUiVisibility = if (isLight) {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else {
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}

@SuppressWarnings("deprecation")
internal fun Activity.setFullscreen(isFullscreen: Boolean) {
    if (isFullscreen) {
        setStatusBarColor(0x00000000)
        setNavigationBarColor(0x00000000)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(!isFullscreen)
    } else {
        window.decorView.apply {
            systemUiVisibility = if (isFullscreen) {
                systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            } else {
                systemUiVisibility and
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv() and
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv() and
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv()
            }
        }
    }
}

internal fun ViewGroup.forChild(action: (View) -> Unit) {
    repeat(childCount) { index ->
        getChildAt(index)?.let { view ->
            when (view) {
                is ViewGroup -> view.forChild(action)
                is View -> action(view)
            }
        }
    }
}

internal fun View.getDisplayRect(): Rect {
    return Rect().apply(::getWindowVisibleDisplayFrame)
}

internal fun View.getGlobalRect(): Rect {
    return Rect().apply(::getGlobalVisibleRect)
}

internal fun View.getGlobalCenterX(): Float {
    return getGlobalRect().run {
        left + width() / 2.0f
    }
}

internal val Context.uiExecutor: Executor
    get() = ContextCompat.getMainExecutor(this)


internal inline fun <reified T : Activity> Context.startClearActivity() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

@ColorInt
internal fun Fragment.getColor(@ColorRes color: Int): Int {
    return requireContext().getColor(color)
}

internal fun View.show() {
    visibility = View.VISIBLE
}

internal fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.setOnDebouncedClickListener(action: () -> Unit) {
    val actionDebouncer = ActionDebouncer(action)
    setOnClickListener {
        actionDebouncer.notifyAction()
    }
}

fun View.removeOnDebouncedClickListener() {
    setOnClickListener(null)
    isClickable = false
}

private class ActionDebouncer(private val action: () -> Unit) {

    companion object {
        const val DEBOUNCE_INTERVAL_MILLISECONDS = 600L
    }

    private var lastActionTime = 0L

    fun notifyAction() {
        val now = SystemClock.elapsedRealtime()

        val millisecondsPassed = now - lastActionTime
        val actionAllowed = millisecondsPassed > DEBOUNCE_INTERVAL_MILLISECONDS
        lastActionTime = now

        if (actionAllowed) {
            action.invoke()
        }
    }
}

fun AppCompatCheckBox.setNewChecked(isChecked: Boolean) {
    if (this.isChecked != isChecked) {
        this.isChecked = isChecked
    }
}

fun TextInputEditText.setNewText(newText: String?) {
    if (this.text.toString() != newText) {
        this.setText(newText)
    }
}


fun TextView.setClickableText(
    clickableTextFragment: String,
    useUnderline: Boolean = false,
    onClickAction: () -> Unit,
    textColor: Int? = null
) {
    text.indexOf(clickableTextFragment)
        .takeIf { it >= 0 }
        ?.let { startIndex ->
            val endIndex = startIndex + clickableTextFragment.length
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    onClickAction.invoke()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = useUnderline
                }
            }
            text = SpannableString(text).apply {
                setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textColor?.let {
                    setSpan(
                        ForegroundColorSpan(it),
                        startIndex, endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            movementMethod = LinkMovementMethod.getInstance()
        } ?: throw Exception("Source TextView does not contain clickable text")
}