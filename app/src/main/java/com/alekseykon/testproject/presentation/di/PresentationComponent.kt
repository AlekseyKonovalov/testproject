package com.alekseykon.testproject.presentation.di

import com.alekseykon.testproject.app.di.AppComponent
import com.alekseykon.testproject.app.di.ComponentHolder
import com.alekseykon.testproject.domain.di.DomainComponent
import com.alekseykon.testproject.presentation.activity.main.MainActivity
import com.alekseykon.testproject.presentation.di.main.MainInject
import com.alekseykon.testproject.presentation.di.main.MainModule
import com.alekseykon.testproject.presentation.di.utils.UtilsModule
import com.alekseykon.testproject.presentation.di.utils.UtilsProvider
import dagger.Component


@PresentationScope
@Component(
    dependencies = [
        AppComponent::class,
        DomainComponent::class
    ],
    modules = [
        MainModule::class,
        UtilsModule::class
    ]
)
internal interface PresentationComponent : MainInject, UtilsProvider {

    companion object : ComponentHolder<PresentationComponent>() {
        override fun provide(): PresentationComponent {
            return DaggerPresentationComponent.builder()
                .appComponent(AppComponent.get())
                .domainComponent(DomainComponent.get())
                .build()
        }
    }

    fun inject(activity: MainActivity)

}