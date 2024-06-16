package com.softcross.moviedetective.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softcross.moviedetective.presentation.home.HomeScreen
import com.softcross.moviedetective.presentation.home.HomeViewModel
import com.softcross.moviedetective.presentation.signin.LoginScreen
import com.softcross.moviedetective.presentation.signup.RegisterScreen
import com.softcross.moviedetective.presentation.splash.SplashScreen

@Composable
fun MovieNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
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
            HomeScreen()
        }
    }
}