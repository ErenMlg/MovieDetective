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

object MovieDetail : Destination {
    override val route: String = "movieDetail"
    val idArg = "id"
    val routeWithArgs = "${MovieDetail.route}/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) {
            type = NavType.IntType
        }
    )
}

object PopularMovies : Destination {
    override val route: String = "popularMovies"
}

object TrendMovies : Destination {
    override val route: String = "trendMovies"
}

object ComingMovies : Destination {
    override val route: String = "comingMovies"
}

object DiscoverMovies : Destination {
    override val route: String = "discoverMovies"
    val genresArg = "genres"
    val routeWithArgs = "$route/{$genresArg}"
    val arguments = listOf(
        navArgument(genresArg) {
            type = NavType.StringType
        }
    )
}

object PopularPeoples : Destination {
    override val route: String = "popularPeoples"
}

