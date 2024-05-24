package com.softcross.moviedetective.navigation

interface Destination {
    val route: String
}

object LoginScreen : Destination {
    override val route: String = "login"
}

object RegisterScreen : Destination {
    override val route: String = "register"
}

object HomeScreen : Destination{
    override val route: String = "home"
    val routeWithArgs = "$route/$"
    fun routeWithArgs(){

    }
}