package com.alekseykon.testproject.presentation.navigation


import androidx.navigation.NavController
import androidx.navigation.NavDirections

internal interface NavManager {
    fun navigate(navDirections: NavDirections)
    fun setup(newNavController: NavController)
}

internal class NavManagerImpl : NavManager {
    private var navController: NavController? = null

    override fun navigate(navDirections: NavDirections) {
        navController?.navigate(navDirections)
    }

    override fun setup(newNavController: NavController) {
        this.navController = newNavController
    }
}
