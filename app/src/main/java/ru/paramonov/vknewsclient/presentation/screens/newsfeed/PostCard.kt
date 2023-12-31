package ru.paramonov.vknewsclient.presentation.screens.newsfeed

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.paramonov.vknewsclient.R
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.StatisticItem
import ru.paramonov.vknewsclient.domain.entity.StatisticType
import ru.paramonov.vknewsclient.presentation.ui.theme.VKRed

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit,
    onAddClickListener: (FeedPost) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            PostHeader(
                feedPost = feedPost,
                onAddClickListener = onAddClickListener
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimationText(text = feedPost.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = feedPost.contentImageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistic(
                statistics = feedPost.statistics,
                onCommentsClickListener = onCommentsClickListener,
                onLikesClickListener = onLikesClickListener,
                isFavorite = feedPost.isLiked
            )
        }
    }
}

@Composable
private fun AnimationText(
    text: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Text(
        text = text,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable {
                expanded = !expanded
            },
        maxLines = if (!expanded) 2 else 10,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun PostHeader(
    feedPost: FeedPost,
    onAddClickListener: (FeedPost) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(56.dp)
                .clip(shape = CircleShape),
            model = feedPost.communityImageUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedPost.datePublication,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        IconButton(onClick = { onAddClickListener(feedPost) }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
private fun Statistic(
    statistics: List<StatisticItem>,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit,
    isFavorite: Boolean
) {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            ImageWithText(
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticsCount(viewsItem.count)
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            ImageWithText(
                iconResId = R.drawable.ic_share,
                text = formatStatisticsCount(sharesItem.count)
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            ImageWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticsCount(commentsItem.count),
                onItemClickListener = {
                    onCommentsClickListener(commentsItem)
                }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            ImageWithText(
                iconResId = if (isFavorite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticsCount(likesItem.count),
                onItemClickListener = {
                    onLikesClickListener(likesItem)
                },
                tint = if (isFavorite) VKRed else MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

private fun formatStatisticsCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
private fun ImageWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: (() -> Unit)? = null,
    tint: Color = MaterialTheme.colorScheme.onSecondary
) {
    val modifier = if (onItemClickListener == null) {
        Modifier
    } else {
        Modifier.clickable { onItemClickListener() }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}