package com.alekseykon.testproject.core.extensions

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> String.jsonToObject(): T? {
    return try {
        Gson().fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        Log.e("GSON", "jsonToObject()", e)
        null
    }
}

inline fun <reified T> T.objectToJson(): String {
    return Gson().toJson(this, object : TypeToken<T>() {}.type)
}