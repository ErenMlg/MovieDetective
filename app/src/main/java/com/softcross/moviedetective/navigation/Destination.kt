package com.softcross.moviedetective.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Splash : Destination {
    override val route: String = "splash"
}

object Login : Destination {
    override val route: String = "login"
}

object Register : Destination {
    override val route: String = "register"
}

object Home : Destination {
    override val route: String = "home"
}