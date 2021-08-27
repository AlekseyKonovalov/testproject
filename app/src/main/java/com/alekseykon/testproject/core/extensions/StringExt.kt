package com.alekseykon.testproject.core.extensions

import android.graphics.Color
import android.text.*
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt


fun String.toSpanned(
    @ColorInt color: Int = Color.BLACK,
    style: Int? = null,
    size: Int? = null,
    isUnderline: Boolean = false,
    action: (() -> Unit)? = null
): Spanned {
    return SpannableStringBuilder(this).apply {
        size?.let {
            setSpan(AbsoluteSizeSpan(it, true), 0, this@toSpanned.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        style?.let {
            setSpan(StyleSpan(it), 0, this@toSpanned.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        setSpan(ForegroundColorSpan(color), 0, this@toSpanned.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        action?.let {
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    action()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.bgColor = Color.TRANSPARENT
                    ds.color = color
                    ds.isUnderlineText = isUnderline
                }
            }, 0, this@toSpanned.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}

operator fun Spanned.plus(value: Spanned): Spanned {
    return TextUtils.concat(this, value) as Spanned
}

fun String.isValidUrl(): Boolean {
    return this.matches(
        Regex("^(https?|ftp)://[-a-zA-Z-а-яА-Я0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z-а-яА-Я0-9+&@#/%=~_|]")
    )
}