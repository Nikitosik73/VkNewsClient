package ru.paramonov.vknewsclient.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.paramonov.vknewsclient.MainViewModel
import ru.paramonov.vknewsclient.domain.PostComment

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val feedPosts = viewModel.feedPosts.observeAsState(emptyList())

    val comments = mutableListOf<PostComment>().apply {
        repeat(20) {
            add(
                PostComment(it)
            )
        }
    }
    
    CommentsScreen(post = feedPosts.value[0], comments = comments)

//    LazyColumn(
//        modifier = Modifier.padding(paddingValues),
//        contentPadding = PaddingValues(
//            top = 16.dp,
//            start = 8.dp,
//            end = 8.dp,
//            bottom = 16.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(feedPosts.value, key = { it.id }) { feedPost ->
//            val dismissState = rememberDismissState()
//
//            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
//                viewModel.deletePost(feedPost)
//            }
//
//            SwipeToDismiss(
//                modifier = Modifier.animateItemPlacement(),
//                state = dismissState,
//                directions = setOf(DismissDirection.EndToStart),
//                background = {},
//                dismissContent = {
//                    PostCard(
//                        feedPost = feedPost,
//                        onViewsClickListener = { statisticItem ->
//                            viewModel.updateCount(feedPost = feedPost, item = statisticItem)
//                        },
//                        onSharesClickListener = { statisticItem ->
//                            viewModel.updateCount(feedPost = feedPost, item = statisticItem)
//                        },
//                        onCommentsClickListener = { statisticItem ->
//                            viewModel.updateCount(feedPost = feedPost, item = statisticItem)
//                        },
//                        onLikesClickListener = { statisticItem ->
//                            viewModel.updateCount(feedPost = feedPost, item = statisticItem)
//                        }
//                    )
//                }
//            )
//        }
//    }
}