package ru.paramonov.vknewsclient.presentation.screens.newsfeed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepository
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.StatisticItem

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadAllNewsFeed()
    }

    private fun loadAllNewsFeed() = viewModelScope.launch {
        val newsFeeds = repository.loadAllNewsFeed()
        _screenState.value = NewsFeedScreenState.Posts(posts = newsFeeds)
    }

    fun loadNextDataNewsFeed() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPost,
            nextDataLoading = true
        )
        loadAllNewsFeed()
    }

    fun changeLikeStatus(feedPost: FeedPost) = viewModelScope.launch {
        repository.changeLikeStatus(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPost)
    }

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

    fun deletePost(feedPost: FeedPost) = viewModelScope.launch {
        repository.deletePost(feedPost = feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPost)
    }
}