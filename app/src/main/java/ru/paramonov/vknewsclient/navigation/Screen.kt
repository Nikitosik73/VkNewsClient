package ru.paramonov.vknewsclient.navigation

import ru.paramonov.vknewsclient.domain.FeedPost

sealed class Screen(
    val route: String
) {
    object NewsFeed : Screen(route = ROUTE_NEWS_FEED)
    object Favorite : Screen(route = ROUTE_FAVORITE)
    object Profile : Screen(route = ROUTE_PROFILE)
    object Home : Screen(route = ROUTE_HOME)
    object Comments : Screen(route = ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String {
            return "$ROUTE_FOR_ARGS/${feedPost.id}"
        }
    }

    companion object {

        const val KEY_FEED_POST_ID = "feed_post_id"

        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"
    }
}
