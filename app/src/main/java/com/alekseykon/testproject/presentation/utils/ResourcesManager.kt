package com.alekseykon.testproject.presentation.utils

import android.content.Context
import androidx.core.content.ContextCompat

internal interface ResourcesManager {
    fun getStringArray(stringId: Int): Array<String>
    fun getString(stringId: Int, vararg formatArgs: Any?): String
    fun getString(stringId: Int): String
    fun getColor(color: Int): Int
    fun getDimension(id: Int): Float
    fun getQuantityString(id: Int, quantity: Int): String
}

internal class ResourcesManagerImpl(private val context: Context) : ResourcesManager {
    override fun getDimension(id: Int) = context.resources.getDimension(id)
    override fun getColor(color: Int): Int = ContextCompat.getColor(context, color)
    override fun getString(stringId: Int, vararg formatArgs: Any?): String = context.resources.getString(stringId, *formatArgs)
    override fun getString(stringId: Int): String = context.resources.getString(stringId)
    override fun getQuantityString(id: Int, quantity: Int) = context.resources.getQuantityString(id, quantity, quantity)
    override fun getStringArray(stringId: Int): Array<String> = context.resources.getStringArray(stringId)
}
