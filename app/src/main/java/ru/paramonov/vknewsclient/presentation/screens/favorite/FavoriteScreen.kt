package ru.paramonov.vknewsclient.presentation.screens.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.paramonov.vknewsclient.R
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.presentation.application.getApplicationComponent
import ru.paramonov.vknewsclient.presentation.screens.newsfeed.PostCard
import ru.paramonov.vknewsclient.presentation.ui.theme.VKRed
import ru.paramonov.vknewsclient.presentation.ui.theme.VkDefault

@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues
) {
    val component = getApplicationComponent()
    val viewModel: FavoriteViewModel = viewModel(factory = component.getViewModelFactory())
    val viewState = viewModel.viewState.collectAsState(FavoriteViewState.Initial)

    FavoriteContent(
        viewState = viewState,
        paddingValues = paddingValues,
        viewModel = viewModel
    )
}

@Composable
fun FavoriteContent(
    viewState: State<FavoriteViewState>,
    paddingValues: PaddingValues,
    viewModel: FavoriteViewModel
) {
    when (val currentState = viewState.value) {
        is FavoriteViewState.FavoriteContent -> {
            if (currentState.content.isEmpty()) {
                EmptyFavorite()
            } else {
                FavoriteListItem(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    content = currentState.content
                )
            }
        }

        is FavoriteViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VkDefault)
            }
        }

        is FavoriteViewState.Initial -> {}
    }
}

@Composable
private fun EmptyFavorite() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = VKRed
            )
            Text(
                text = stringResource(id = R.string.empty_favorites),
                fontSize = 18.sp,
                color = VkDefault,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FavoriteListItem(
    viewModel: FavoriteViewModel,
    paddingValues: PaddingValues,
    content: List<FeedPost>
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues = paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 8.dp,
            start = 8.dp,
            end = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = content,
            key = { it.id }
        ) { favoritePost ->
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                SideEffect {
                    viewModel.remove(feedPost = favoritePost)
                }
            }

            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {},
                dismissContent = {
                    PostCard(
                        feedPost = favoritePost,
                        onCommentsClickListener = {},
                        onLikesClickListener = {},
                        onAddClickListener = {}
                    )
                }
            )
        }
    }
}