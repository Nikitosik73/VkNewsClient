package ru.paramonov.vknewsclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.screens.comments.CommentsViewModel

class CommentsViewModelFactory(
    private val feedPost: FeedPost
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CommentsViewModel::class.java) {
            return CommentsViewModel(feedPost = feedPost) as T
        }
        throw IllegalStateException("Unable constructor viewModel")
    }
}