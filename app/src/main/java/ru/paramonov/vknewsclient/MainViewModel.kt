package ru.paramonov.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.PostComment
import ru.paramonov.vknewsclient.domain.StatisticItem
import ru.paramonov.vknewsclient.ui.HomeScreenState

class MainViewModel : ViewModel() {

    private val posts = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(id = it))
        }
    }

    private val initialState = HomeScreenState.Posts(posts = posts)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    private var saveState: HomeScreenState = initialState

    fun showComments(feedPost: FeedPost) {
        _screenState.value = saveState
        _screenState.value = HomeScreenState.Comments(
            feedPost = feedPost,
            comments = comments
        )
    }

    fun closeComments() {
        _screenState.value = saveState
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = _screenState.value
        if (currentState !is HomeScreenState.Posts) return

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
        _screenState.value = HomeScreenState.Posts(posts = modifiedFeedPosts)
    }

    fun deletePost(feedPost: FeedPost) {
        val currentState = _screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(posts = modifiedList)
    }
}