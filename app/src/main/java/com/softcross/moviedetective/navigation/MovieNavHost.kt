package com.softcross.moviedetective.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softcross.moviedetective.presentation.detail.actorDetail.ActorDetailScreen
import com.softcross.moviedetective.presentation.detail.movieDetail.MovieDetailScreen
import com.softcross.moviedetective.presentation.detail.seriesDetail.SeriesDetailScreen
import com.softcross.moviedetective.presentation.home.HomeScreen
import com.softcross.moviedetective.presentation.home.moreMovies.MoreMovieScreen
import com.softcross.moviedetective.presentation.home.popularPeoples.PopularPeoplesScreen
import com.softcross.moviedetective.presentation.series.SeriesScreen
import com.softcross.moviedetective.presentation.signin.LoginScreen
import com.softcross.moviedetective.presentation.signup.RegisterScreen
import com.softcross.moviedetective.presentation.splash.SplashScreen

@Composable
fun MovieNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = Splash.route,
        modifier = modifier
    ) {
        composable(route = Splash.route) {
            SplashScreen(
                onEntryNavigate = remember { { navHostController.navigate(Login.route) } },
                onLogged = remember {
                    {
                        navHostController.navigate(Home.route) {
                            popUpTo(Splash.route)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        composable(route = Login.route) {
            LoginScreen(
                onCreateUser = remember { { navHostController.navigate(Register.route) } },
                onSuccess = remember { { navHostController.navigate(Home.route) } }
            )
        }
        composable(route = Register.route) {
            RegisterScreen(
                onSuccess = remember { { navHostController.navigate(Home.route) } },
                onLogin = remember { { navHostController.navigate(Login.route) } }
            )
        }
        composable(route = Home.route) {
            HomeScreen(
                onExit = remember { { navHostController.navigate(Login.route) } },
                onPopularPeoples = remember { { navHostController.navigate(PopularPeoples.route) } },
                onMovieClick = remember { { navHostController.navigate("${MovieDetail.route}/${it}") } },
                onActorClick = remember { { navHostController.navigate("${ActorDetail.route}/${it}") } },
                onMoreViewClick = remember { { idArg, genreArg -> navHostController.navigate("${MoreMovie.route}/$idArg/$genreArg") } }
            )
        }
        composable(route = MovieDetail.routeWithArgs, arguments = MovieDetail.arguments) {
            MovieDetailScreen(
                onMovieClick = remember { { navHostController.navigate("${MovieDetail.route}/${it}") } },
                onActorClick = remember { { navHostController.navigate("${ActorDetail.route}/${it}") } },
                onPosterClick = {},
                onBackdropClick = {},
                onVideoClick = {},
                onBackClick = remember { { navHostController.navigateUp() } }
            )
        }
        composable(route = PopularPeoples.route) {
            PopularPeoplesScreen()
        }
        composable(route = ActorDetail.routeWithArgs, arguments = ActorDetail.arguments) {
            ActorDetailScreen(
                onSeriesClick = remember { { navHostController.navigate("${SeriesDetail.route}/${it}") } },
                onMovieClick = remember { { navHostController.navigate("${MovieDetail.route}/${it}") } },
                onPhotoClick = {},
                onBackClick = remember { { navHostController.navigateUp() } }
            )
        }
        composable(route = MoreMovie.routeWithArgs, arguments = MoreMovie.arguments) {
            MoreMovieScreen(
                dataTypeID = it.arguments?.getInt(ARGS_ID) ?: 0,
                genreList = it.arguments?.getString(ARGS_GENRE),
                onClick = remember { { idArg -> navHostController.navigate("${MovieDetail.route}/${idArg}") } }
            )
        }
        composable(route = Series.route) {
            SeriesScreen(
                onSeriesClick = remember { { navHostController.navigate("${SeriesDetail.route}/${it}") } }
            )
        }
        composable(route = SeriesDetail.routeWithArgs, arguments = SeriesDetail.arguments) {
            SeriesDetailScreen(
                onSeriesClick = remember { { navHostController.navigate("${SeriesDetail.route}/${it}") } },
                onActorClick = remember { { navHostController.navigate("${ActorDetail.route}/${it}") } },
                onPosterClick = {},
                onBackdropClick = {},
                onVideoClick = {},
                onBackClick = remember { { navHostController.navigateUp() } }
            )
        }
    }
}