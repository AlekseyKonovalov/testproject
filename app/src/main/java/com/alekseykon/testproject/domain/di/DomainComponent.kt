package com.alekseykon.testproject.domain.di

import com.alekseykon.testproject.app.di.ComponentHolder
import com.alekseykon.testproject.data.di.DataComponent
import com.alekseykon.testproject.domain.di.modules.main.MainModule
import com.alekseykon.testproject.domain.di.modules.main.MainProvide
import com.alekseykon.testproject.domain.di.modules.registration.RegistrationModule
import com.alekseykon.testproject.domain.di.modules.registration.RegistrationProvide
import dagger.Component


@DomainScope
@Component(
    dependencies = [
        DataComponent::class
    ],
    modules = [
        RegistrationModule::class,
        MainModule::class
    ]
)
internal interface DomainComponent : RegistrationProvide, MainProvide {

    companion object : ComponentHolder<DomainComponent>() {
        override fun provide(): DomainComponent {
            return DaggerDomainComponent.builder()
                .dataComponent(DataComponent.get())
                .build()
        }
    }

}