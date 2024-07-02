package com.softcross.moviedetective.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softcross.moviedetective.R
import com.softcross.moviedetective.navigation.BottomNavigationBar
import com.softcross.moviedetective.navigation.Home
import com.softcross.moviedetective.navigation.MoreMovie
import com.softcross.moviedetective.navigation.MovieNavHost
import com.softcross.moviedetective.navigation.Series
import com.softcross.moviedetective.presentation.theme.MovieDetectiveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDetectiveTheme {
                window.navigationBarColor =
                    if (isSystemInDarkTheme()) resources.getColor(R.color.blackgray) else resources.getColor(
                        R.color.white
                    )
                val navController = rememberNavController()
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Home.route -> bottomBarState.value = true
                    Series.route -> bottomBarState.value = true
                    else -> bottomBarState.value = false
                }
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            bottomBarState = bottomBarState
                        )
                    }
                ) { innerPadding ->
                    MovieNavHost(
                        navHostController = navController,
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    )
                }
            }
        }
    }
}
