package ru.paramonov.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.paramonov.vknewsclient.domain.entity.FeedPost

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route = route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(feedPost: FeedPost) {
        navHostController.navigate(route = Screen.Comments.getRouteWithArgs(feedPost))
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState = remember {
    NavigationState(navHostController = navHostController)
}