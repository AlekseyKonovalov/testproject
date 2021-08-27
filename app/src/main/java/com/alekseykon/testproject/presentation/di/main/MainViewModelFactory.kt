package com.alekseykon.testproject.presentation.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alekseykon.testproject.presentation.di.PresentationScope
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@PresentationScope
internal class MainModelFactory @Inject constructor(
    @Named(MainModule.MAP_MODELS)
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}
