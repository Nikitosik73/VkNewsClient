package ru.paramonov.vknewsclient.presentation.screens.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.paramonov.vknewsclient.R
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment
import ru.paramonov.vknewsclient.domain.entity.StatisticItem
import ru.paramonov.vknewsclient.domain.entity.StatisticType
import ru.paramonov.vknewsclient.presentation.application.getApplicationComponent
import ru.paramonov.vknewsclient.presentation.ui.theme.VKRed
import ru.paramonov.vknewsclient.presentation.ui.theme.VkDefault

@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost
) {
    val component = getApplicationComponent()
        .getCommentsScreenFactory()
        .create(feedPost = feedPost)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)

    CommentsScreenContent(
        screenState = screenState,
        onBackPressed = onBackPressed,
         viewModel = viewModel
    )
}

@Composable
private fun CommentsScreenContent(
    screenState: State<CommentsScreenState>,
    onBackPressed: () -> Unit,
    viewModel: CommentsViewModel
) {
    when (val currentState = screenState.value) {
        is CommentsScreenState.Comments -> {
            Comments(
                comments = currentState.comments,
                onBackPressed = onBackPressed,
                viewModel = viewModel
            )
        }

        is CommentsScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VkDefault)
            }
        }

        is CommentsScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Comments(
    comments: List<PostComment>,
    onBackPressed: () -> Unit,
    viewModel: CommentsViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.comments),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(
                top = 16.dp, bottom = 72.dp,
                start = 8.dp, end = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = comments,
                key = { it.fromId }
            ) { comment ->
                CommentItem(
                    comment = comment,
                    onLikeClickListener = {
                        viewModel.changeLikeStatusComments(comment = comment)
                    }
                )
            }
        }
    }
}

@Composable
fun CommentItem(
    comment: PostComment,
    onLikeClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = comment.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Column {
                Text(
                    text = comment.authorName,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = comment.commentText,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = comment.datePublication,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 12.sp
                )
            }
        }
        Row {
            val itemLike = comment.likes.getItemWithType(StatisticType.LIKES)
            ImageWithText(
                iconResId = if (comment.isLiked) R.drawable.ic_like_set else R.drawable.ic_like,
                text = itemLike.count.toString(),
                tint = if (comment.isLiked) VKRed else MaterialTheme.colorScheme.onSecondary,
                onItemClickListener = onLikeClickListener
            )
        }
    }
}

private fun List<StatisticItem>.getItemWithType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException("Cannot find be type")
}

@Composable
private fun ImageWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSecondary
) {
    Row(
        modifier = Modifier.clickable { onItemClickListener() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}