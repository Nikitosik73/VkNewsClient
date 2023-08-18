package ru.paramonov.vknewsclient.presentation.screens.newsfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val posts = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(
                    id = it,
                    contentText = "Cont/ent $it"
                )
            )
        }
    }

    private val initialState = NewsFeedScreenState.Posts(posts = posts)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val currentFeedPosts = currentState.posts.toMutableList()
        val currentStatistics = feedPost.statistics
        val modifiedStatistics = currentStatistics.toMutableList().apply {
            replaceAll { currentItem ->
                if (currentItem.type == item.type) {
                    currentItem.copy(count = currentItem.count + 1)
                } else {
                    currentItem
                }
            }
        }
        val modifiedFeedPost = feedPost.copy(statistics = modifiedStatistics)
        val modifiedFeedPosts = currentFeedPosts.apply {
            replaceAll { currentFeedPost ->
                if (currentFeedPost.id == modifiedFeedPost.id) {
                    modifiedFeedPost
                } else {
                    currentFeedPost
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = modifiedFeedPosts)
    }

    fun deletePost(feedPost: FeedPost) {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = modifiedList)
    }
}