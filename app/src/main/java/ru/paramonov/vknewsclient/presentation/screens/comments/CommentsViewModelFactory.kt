package ru.paramonov.vknewsclient.presentation.screens.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.paramonov.vknewsclient.domain.FeedPost

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