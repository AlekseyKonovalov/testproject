package com.alekseykon.testproject.presentation.activity.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.alekseykon.testproject.R
import com.alekseykon.testproject.presentation.activity.base.IntegrationBaseActivity
import com.alekseykon.testproject.presentation.di.PresentationComponent
import com.alekseykon.testproject.presentation.navigation.NavManager
import javax.inject.Inject


internal class MainActivity : IntegrationBaseActivity() {

    override val resIdLayout: Int = R.layout.activity_main

    @Inject
    lateinit var navManager: NavManager

    override fun onCreate(savedInstanceState: Bundle?) {
        PresentationComponent.get().inject(this)
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        val hostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navManager.setup(hostFragment.navController)
    }

}