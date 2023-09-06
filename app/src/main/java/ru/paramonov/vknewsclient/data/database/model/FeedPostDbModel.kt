package ru.paramonov.vknewsclient.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.paramonov.vknewsclient.domain.entity.StatisticItem

@Entity(tableName = "feed_posts")
data class FeedPostDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val datePublication: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val viewsCount: Int,
    val sharesCount: Int,
    val commentsCount: Int,
    val likesCount: Int,
    val isLiked: Boolean
)