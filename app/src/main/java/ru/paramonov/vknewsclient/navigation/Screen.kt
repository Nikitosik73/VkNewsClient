package ru.paramonov.vknewsclient.navigation

import android.net.Uri
import com.google.gson.Gson
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
            val feedPostJson = Gson().toJson(feedPost).encode()
            return "$ROUTE_FOR_ARGS/$feedPostJson"
        }
    }

    companion object {

        const val KEY_FEED_POST = "feed_post"

        private const val ROUTE_HOME = "home"
        private const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
        private const val ROUTE_NEWS_FEED = "news_feed"
        private const val ROUTE_FAVORITE = "favorite"
        private const val ROUTE_PROFILE = "profile"
    }
}

private fun String.encode() = Uri.encode(this)
