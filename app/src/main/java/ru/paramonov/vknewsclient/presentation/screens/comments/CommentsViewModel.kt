package ru.paramonov.vknewsclient.presentation.screens.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment
import ru.paramonov.vknewsclient.domain.usecase.ChangeLikesStatusCommentUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetCommentsUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val changeLikesStatusCommentUseCase: ChangeLikesStatusCommentUseCase
) : ViewModel() {

    val screenState: Flow<CommentsScreenState> = getCommentsUseCase(feedPost = feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
        .onStart { emit(CommentsScreenState.Loading) }

    fun changeLikeStatusComments(comment: PostComment) {
        viewModelScope.launch {
            changeLikesStatusCommentUseCase(comment = comment, ownerId = feedPost.communityId)
        }
    }
}