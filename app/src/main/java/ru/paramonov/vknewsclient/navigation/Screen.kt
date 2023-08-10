package ru.paramonov.vknewsclient.navigation

sealed class Screen(
    val route: String
) {
    object NewsFeed : Screen(route = ROUTE_NEWS_FEED)
    object Favorite : Screen(route = ROUTE_FAVORITE)
    object Profile : Screen(route = ROUTE_PROFILE)

    private companion object {

        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"
    }
}
