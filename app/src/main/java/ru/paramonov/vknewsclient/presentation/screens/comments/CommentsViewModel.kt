package ru.paramonov.vknewsclient.presentation.screens.comments

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.usecase.GetCommentsUseCase

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState: Flow<CommentsScreenState> = getCommentsUseCase(feedPost = feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
        .onStart { emit(CommentsScreenState.Loading) }
}