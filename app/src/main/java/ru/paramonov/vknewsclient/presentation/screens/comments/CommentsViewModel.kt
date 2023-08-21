package ru.paramonov.vknewsclient.presentation.screens.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepository
import ru.paramonov.vknewsclient.domain.FeedPost

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = CommentsScreenState.Loading
        loadComments(feedPost = feedPost)
    }

    private fun loadComments(feedPost: FeedPost) = viewModelScope.launch {
        val comments = repository.loadComments(feedPost = feedPost)
        _screenState.value = CommentsScreenState.Comments(
            feedPost = feedPost,
            comments = comments
        )
    }
}