package ru.paramonov.vknewsclient.presentation.screens.newsfeed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepository
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.extensions.mergeWith

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)
    private val allNewsFeedFlow = repository.allNewsFeed

    private val nextLoadDataEvents = MutableSharedFlow<Unit>()
    private val nextLoadDataFlow = flow {
        nextLoadDataEvents.collect {
            emit(
                NewsFeedScreenState.Posts(
                    posts = allNewsFeedFlow.value,
                    nextDataLoading = true
                )
            )
        }
    }

    val screenState: Flow<NewsFeedScreenState> = allNewsFeedFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(another = nextLoadDataFlow)

    fun loadNextDataNewsFeed() = viewModelScope.launch {
        nextLoadDataEvents.emit(Unit)
        repository.loadNextData()
    }

    fun changeLikeStatus(feedPost: FeedPost) = viewModelScope.launch {
        repository.changeLikeStatus(feedPost)
    }

    fun deletePost(feedPost: FeedPost) = viewModelScope.launch {
        repository.deletePost(feedPost = feedPost)
    }
}