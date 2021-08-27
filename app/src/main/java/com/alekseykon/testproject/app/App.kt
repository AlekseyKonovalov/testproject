package com.alekseykon.testproject.app

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.FirebaseApp

internal class App: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        FirebaseApp.initializeApp(this)
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }

}