package ru.paramonov.vknewsclient.data.mapper

import ru.paramonov.vknewsclient.data.network.model.profile.ProfileResponseDto
import ru.paramonov.vknewsclient.data.network.model.profile.post.WallPostsResponseDto
import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.entity.StatisticItem
import ru.paramonov.vknewsclient.domain.entity.StatisticType
import ru.paramonov.vknewsclient.domain.entity.WallPost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ProfileMapper @Inject constructor() {

    fun mapResponseToProfile(
        response: ProfileResponseDto
    ): Profile {
        val profileDto = response.profileContent

        return Profile(
            id = profileDto.id,
            fullName = "${profileDto.firstName} ${profileDto.lastName}",
            screenName = profileDto.accountName,
            profilePhoto = profileDto.profilePhoto,
            homeTown = profileDto.homeTown,
            bDate = profileDto.bDate
        )
    }

    fun mapResponseToWallPosts(response: WallPostsResponseDto): List<WallPost> {
        val result = mutableListOf<WallPost>()

        val posts = response.wallPostContent.posts
        val profiles = response.wallPostContent.profiles

        for (post in posts) {
            val profile = profiles.firstOrNull { it.id == post.ownerId }?: continue

            val wallPost = WallPost(
                id = post.id,
                ownerId = post.ownerId,
                communityName = "${profile.firstName} ${profile.lastName}",
                datePublication = mapTimestampToDate(post.date),
                communityImageUrl = profile.photoUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                    StatisticItem(type = StatisticType.SHARES, count = post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticItem(type = StatisticType.LIKES, count = post.likes.count)
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(wallPost)
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}