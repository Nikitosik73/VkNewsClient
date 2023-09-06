package ru.paramonov.vknewsclient.presentation.screens.main

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.paramonov.vknewsclient.navigation.AppNavGraph
import ru.paramonov.vknewsclient.navigation.rememberNavigationState
import ru.paramonov.vknewsclient.presentation.screens.comments.CommentsScreen
import ru.paramonov.vknewsclient.presentation.screens.favorite.FavoriteScreen
import ru.paramonov.vknewsclient.presentation.screens.newsfeed.NewsFeedScreen
import ru.paramonov.vknewsclient.presentation.screens.profile.ProfileScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )

                items.forEach { item ->
                    val selectedItem = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selectedItem,
                        onClick = {
                            if (!selectedItem) {
                                navigationState.navigateTo(route = item.screen.route)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = innerPadding,
                    onCommentsClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    feedPost = feedPost
                )
            },
            favoriteScreenContent = {
                FavoriteScreen(
                    paddingValues = innerPadding
                )
            },
            profileScreenContent = {
                ProfileScreen(
                    paddingValues = innerPadding
                )
            }
        )
    }
}

