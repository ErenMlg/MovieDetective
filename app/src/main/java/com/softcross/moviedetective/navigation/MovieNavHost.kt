package com.softcross.moviedetective.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softcross.moviedetective.common.extensions.listToString
import com.softcross.moviedetective.presentation.detail.MovieDetailScreen
import com.softcross.moviedetective.presentation.home.HomeScreen
import com.softcross.moviedetective.presentation.home.comingMovies.ComingMoviesScreen
import com.softcross.moviedetective.presentation.home.discoverMovies.DiscoverMoviesScreen
import com.softcross.moviedetective.presentation.home.popularMovies.PopularMoviesScreen
import com.softcross.moviedetective.presentation.home.popularPeoples.PopularPeoplesScreen
import com.softcross.moviedetective.presentation.home.trendMovies.TrendMoviesScreen
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
                onEntryNavigate = {
                    navHostController.navigate(Login.route)
                },
                onLogged = {
                    navHostController.navigate(Home.route) {
                        popUpTo(Splash.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Login.route) {
            LoginScreen(
                onCreateUser = {
                    navHostController.navigate(Register.route)
                },
                onSuccess = {
                    navHostController.navigate(Home.route)
                }
            )
        }
        composable(route = Register.route) {
            RegisterScreen(
                onSuccess = {
                    navHostController.navigate(Home.route)
                },
                onLogin = {
                    navHostController.navigate(Login.route)
                }
            )
        }
        composable(route = Home.route) {
            HomeScreen(
                onExit = {
                    navHostController.navigate(Login.route)
                },
                onPopularMovies = {
                    navHostController.navigate(PopularMovies.route)
                },
                onTrendMovies = {
                    navHostController.navigate(TrendMovies.route)
                },
                onPopularPeoples = {
                    navHostController.navigate(PopularPeoples.route)
                },
                onDiscoverMovies = {
                    navHostController.navigate("${DiscoverMovies.route}/${it.listToString()}")
                },
                onComingMovies = {
                    navHostController.navigate(ComingMovies.route)
                },
                onMovieClick = {
                    navHostController.navigate("${MovieDetail.route}/${it}")
                }
            )
        }
        composable(route = MovieDetail.routeWithArgs, arguments = MovieDetail.arguments) {
            MovieDetailScreen()
        }
        composable(route = PopularMovies.route) {
            PopularMoviesScreen()
        }
        composable(route = TrendMovies.route) {
            TrendMoviesScreen()
        }
        composable(route = PopularPeoples.route) {
            PopularPeoplesScreen()
        }
        composable(route = DiscoverMovies.routeWithArgs, arguments = DiscoverMovies.arguments) {
            DiscoverMoviesScreen(genreList = it.arguments?.getString(DiscoverMovies.genresArg))
        }
        composable(route = ComingMovies.route) {
            ComingMoviesScreen()
        }
    }
}