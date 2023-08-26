package ru.paramonov.vknewsclient.presentation.screens.comments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.usecase.GetCommentsUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val screenState: Flow<CommentsScreenState> = getCommentsUseCase(feedPost = feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
        .onStart { emit(CommentsScreenState.Loading) }
}