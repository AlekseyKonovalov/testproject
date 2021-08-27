package com.alekseykon.testproject.data.di


import com.alekseykon.testproject.app.di.AppComponent
import com.alekseykon.testproject.app.di.ComponentHolder
import com.alekseykon.testproject.data.di.modules.repositories.RepositoriesModule
import com.alekseykon.testproject.data.di.modules.repositories.RepositoriesProvide
import com.alekseykon.testproject.data.di.modules.retrofit.RetrofitModule
import com.alekseykon.testproject.data.di.modules.services.ServicesModule
import com.alekseykon.testproject.data.di.modules.services.ServicesProvider
import dagger.Component


@DataScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ServicesModule::class,
        RepositoriesModule::class,
        RetrofitModule::class
    ]
)
internal interface DataComponent : ServicesProvider, RepositoriesProvide {

    companion object : ComponentHolder<DataComponent>() {
        override fun provide(): DataComponent {
            return DaggerDataComponent.builder()
                .appComponent(AppComponent.get())
                .build()
        }
    }

}