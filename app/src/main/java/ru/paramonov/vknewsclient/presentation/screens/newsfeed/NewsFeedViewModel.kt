package ru.paramonov.vknewsclient.presentation.screens.newsfeed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.usecase.ChangeLikeStatusUseCase
import ru.paramonov.vknewsclient.domain.usecase.DeletePostUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetAllNewsFeedUseCase
import ru.paramonov.vknewsclient.domain.usecase.LoadNextDataUseCase
import ru.paramonov.vknewsclient.extensions.mergeWith

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught by exception handler")
    }

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAllNewsFeedUseCase = GetAllNewsFeedUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)

    private val allNewsFeedFlow = getAllNewsFeedUseCase()

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
        loadNextDataUseCase()
    }

    fun changeLikeStatus(feedPost: FeedPost) = viewModelScope.launch(exceptionHandler) {
        changeLikeStatusUseCase(feedPost = feedPost)
    }

    fun deletePost(feedPost: FeedPost) = viewModelScope.launch(exceptionHandler) {
        deletePostUseCase(feedPost = feedPost)
    }
}