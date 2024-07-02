package com.softcross.moviedetective.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softcross.moviedetective.presentation.components.CustomText

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {
    val items = listOf(
        BottomNavItem.HomeScreen,
        BottomNavItem.TVSeriesScreen,
        BottomNavItem.SearchScreen,
        BottomNavItem.WatchListScreen,
        BottomNavItem.ProfileScreen
    )
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigation(
            modifier = Modifier.padding(
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
            ),
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            elevation = 8.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.padding()
                        )
                    },
                    label = {
                        CustomText(
                            text = item.title,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 8.dp, )
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = remember {
                        {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.secondary,
                    unselectedContentColor = Color.DarkGray,
                    alwaysShowLabel = false
                )
            }
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun BottomNavigationBarPreview() {
    val x = remember {
        mutableStateOf(false)
    }
    MaterialTheme {
        BottomNavigationBar(
            navController = rememberNavController(),
            bottomBarState = x
        )
    }
}