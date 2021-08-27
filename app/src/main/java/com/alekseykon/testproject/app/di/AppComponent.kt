package com.alekseykon.testproject.app.di

import android.app.Application
import com.alekseykon.testproject.app.App
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class
    ]
)
internal interface AppComponent : AppProvider {

    companion object : ComponentHolder<AppComponent>() {
        override fun provide(): AppComponent {
            return DaggerAppComponent.builder()
                .with(App.INSTANCE)
                .build()
        }
    }


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun with(app: Application): Builder

        fun build(): AppComponent
    }

}