package com.softcross.moviedetective.navigation

import com.softcross.moviedetective.R

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
) {
    data object HomeScreen : BottomNavItem(
        title = "Home",
        icon = R.drawable.icon_home,
        route = "home"
    )

    data object TVSeriesScreen : BottomNavItem(
        title = "TV Series",
        icon = R.drawable.icon_series,
        route = "series"
    )

    data object SearchScreen : BottomNavItem(
        title = "Search",
        icon = R.drawable.icon_search,
        route = "search"
    )

    data object WatchListScreen : BottomNavItem(
        title = "Watch List",
        icon = R.drawable.icon_watchlist,
        route = "watchList"
    )

    data object ProfileScreen : BottomNavItem(
        title = "Profile",
        icon = R.drawable.icon_profile,
        route = "profile"
    )
}

