package ru.paramonov.vknewsclient.presentation.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.usecase.GetAllFavoritesNewsFeed
import ru.paramonov.vknewsclient.domain.usecase.RemoveFromFavoritesUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getAllFavoritesNewsFeed: GetAllFavoritesNewsFeed,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("FavoriteViewModel", "Exception caught by exception handler")
    }

    val viewState: Flow<FavoriteViewState> = getAllFavoritesNewsFeed()
        .map { FavoriteViewState.FavoriteContent(content = it) as FavoriteViewState }
        .onStart { emit(FavoriteViewState.Loading) }

    fun remove(feedPost: FeedPost) = viewModelScope.launch(exceptionHandler) {
        Log.d("remove_from_database", feedPost.toString())
        removeFromFavoritesUseCase(feedPost = feedPost)
    }
}