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
    val routeWithArgs = "${route}/{$ARGS_ID}"
    val arguments = listOf(
        navArgument(ARGS_ID) {
            type = NavType.IntType
        }
    )
}

object ActorDetail : Destination {
    override val route: String = "actorDetail"
    val routeWithArgs = "${route}/{$ARGS_ID}"
    val arguments = listOf(
        navArgument(ARGS_ID) {
            type = NavType.IntType
        }
    )
}

object MoreMovie : Destination {
    override val route: String = "moreMovie"
    val routeWithArgs = "${route}/{$ARGS_ID}/{$ARGS_GENRE}"
    val arguments = listOf(
        navArgument(ARGS_ID) {
            type = NavType.IntType
        },
        navArgument(ARGS_GENRE) {
            type = NavType.StringType
            nullable = true
        }
    )
}

object PopularPeoples : Destination {
    override val route: String = "popularPeoples"
}

object Series : Destination {
    override val route: String = "series"
}

object SeriesDetail : Destination {
    override val route: String = "seriesDetail"
    val routeWithArgs = "${route}/{$ARGS_ID}"
    val arguments = listOf(
        navArgument(ARGS_ID) {
            type = NavType.IntType
        }
    )
}


const val ARGS_ID = "id"
const val ARGS_GENRE = "genre"