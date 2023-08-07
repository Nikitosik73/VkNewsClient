package ru.paramonov.vknewsclient.domain

import ru.paramonov.vknewsclient.R

data class FeedPost(
    val communityName: String = "dev/null",
    val datePublication: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 24),
        StatisticItem(type = StatisticType.LIKES, count = 101)
    )
)
