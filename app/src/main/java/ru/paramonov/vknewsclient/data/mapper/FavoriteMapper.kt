package ru.paramonov.vknewsclient.data.mapper

import ru.paramonov.vknewsclient.data.database.model.FeedPostDbModel
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.StatisticItem
import ru.paramonov.vknewsclient.domain.entity.StatisticType
import javax.inject.Inject

class FavoriteMapper @Inject constructor() {

    fun mapDbModelToEntity(db: FeedPostDbModel): FeedPost = FeedPost(
        id = db.id,
        communityId = db.communityId,
        communityName = db.communityName,
        datePublication = db.datePublication,
        communityImageUrl = db.communityImageUrl,
        contentText = db.contentText,
        contentImageUrl = db.contentImageUrl,
        statistics = listOf(
            StatisticItem(type = StatisticType.VIEWS, count = db.viewsCount),
            StatisticItem(type = StatisticType.SHARES, count = db.sharesCount),
            StatisticItem(type = StatisticType.COMMENTS, count = db.commentsCount),
            StatisticItem(type = StatisticType.LIKES, count = db.likesCount),
        ),
        isLiked = db.isLiked
    )

    fun mapEntityToDbModel(entity: FeedPost): FeedPostDbModel = FeedPostDbModel(
        id = entity.id,
        communityId = entity.communityId,
        communityName = entity.communityName,
        datePublication = entity.datePublication,
        communityImageUrl = entity.communityImageUrl,
        contentText = entity.contentText,
        contentImageUrl = entity.contentImageUrl,
        viewsCount = entity.statistics.getItemByType(StatisticType.VIEWS).count,
        sharesCount = entity.statistics.getItemByType(StatisticType.SHARES).count,
        commentsCount = entity.statistics.getItemByType(StatisticType.COMMENTS).count,
        likesCount = entity.statistics.getItemByType(StatisticType.LIKES).count,
        isLiked = entity.isLiked
    )

    private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
        return this.find { it.type == type } ?: throw IllegalStateException()
    }
}