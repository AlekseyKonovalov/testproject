package com.alekseykon.testproject.app.di


internal abstract class ComponentHolder<C> {

    @Volatile
    private var component: C? = null

    private val lock = Any()

    protected abstract fun provide(): C

    fun get(): C {
        return component ?: synchronized(lock) {
            component ?: provide().also { component = it }
        }
    }

}