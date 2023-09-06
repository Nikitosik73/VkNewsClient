package ru.paramonov.vknewsclient.presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.paramonov.vknewsclient.navigation.AppNavGraph
import ru.paramonov.vknewsclient.navigation.NavigationState
import ru.paramonov.vknewsclient.navigation.rememberNavigationState
import ru.paramonov.vknewsclient.presentation.screens.comments.CommentsScreen
import ru.paramonov.vknewsclient.presentation.screens.favorite.FavoriteScreen
import ru.paramonov.vknewsclient.presentation.screens.newsfeed.NewsFeedScreen
import ru.paramonov.vknewsclient.presentation.screens.profile.ProfileScreen
import ru.paramonov.vknewsclient.presentation.ui.theme.VkDefault

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = { BottomNavBar(navigationState = navigationState) }
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

@Composable
private fun BottomNavBar(navigationState: NavigationState) {

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favorite,
        NavigationItem.Profile
    )

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { item ->
                BottomNavItem(
                    navigationState = navigationState,
                    item = item
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    navigationState: NavigationState,
    item: NavigationItem
) {
    val navBackStackEntry
            by navigationState.navHostController.currentBackStackEntryAsState()

    val selectedItem = navBackStackEntry?.destination?.hierarchy?.any {
        it.route == item.screen.route
    } == true

    val background =
        if (selectedItem) VkDefault else Color.Transparent

    val contentColor =
        if (selectedItem) Color.White else MaterialTheme.colorScheme.onSecondary

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(shape = CircleShape)
            .background(color = background)
            .clickable { navigationState.navigateTo(item.screen.route) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor
            )
            AnimatedVisibility(visible = selectedItem) {
                Text(
                    text = stringResource(id = item.titleResId),
                    color = contentColor
                )
            }
        }
    }
}