package ru.paramonov.vknewsclient.data.mapper

import ru.paramonov.vknewsclient.data.network.model.NewsFeedResponseDto
import ru.paramonov.vknewsclient.data.network.model.comments.CommentsResponseDto
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment
import ru.paramonov.vknewsclient.domain.entity.StatisticItem
import ru.paramonov.vknewsclient.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToNewsFeed(response: NewsFeedResponseDto): List<FeedPost> {

        val result = mutableListOf<FeedPost>()

        val posts = response.newsFeedContent.posts
        val groups = response.newsFeedContent.groups

        for(post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                datePublication = mapTimestampToDate(post.date),
                communityName = group.name,
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, count = post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                    StatisticItem(type = StatisticType.SHARES, count = post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(feedPost)
        }
        return result
    }

    fun mapResponseToPostComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()

        val comments = response.commentsContent.comments
        val profiles = response.commentsContent.profiles

        for (comment in comments) {
            if (comment.text.isBlank()) continue

            val profile = profiles.firstOrNull { it.id == comment.fromId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${profile.lastName} ${profile.firstName}",
                commentText = comment.text,
                datePublication = mapTimestampToDate(comment.date),
                avatarUrl = profile.photoUrl
            )
            result.add(postComment)
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}