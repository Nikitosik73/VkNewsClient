package ru.paramonov.vknewsclient.presentation.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import ru.paramonov.vknewsclient.R
import ru.paramonov.vknewsclient.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val unfocusedIcon: ImageVector,
    val focusedIcon: ImageVector
) {

    object Home : NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_main,
        unfocusedIcon = Icons.Outlined.Home,
        focusedIcon = Icons.Rounded.Home
    )

    object Favorite : NavigationItem(
        screen = Screen.Favorite,
        titleResId = R.string.navigation_item_favorite,
        unfocusedIcon = Icons.Outlined.FavoriteBorder,
        focusedIcon = Icons.Rounded.Favorite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        unfocusedIcon = Icons.Outlined.Person,
        focusedIcon = Icons.Rounded.AccountCircle
    )
}
