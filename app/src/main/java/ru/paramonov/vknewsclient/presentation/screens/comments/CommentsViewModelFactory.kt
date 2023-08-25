package ru.paramonov.vknewsclient.presentation.screens.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.paramonov.vknewsclient.domain.entity.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val application: Application
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CommentsViewModel::class.java) {
            return CommentsViewModel(feedPost = feedPost, application = application) as T
        }
        throw IllegalStateException("Unable constructor viewModel")
    }
}