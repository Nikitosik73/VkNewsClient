package ru.paramonov.vknewsclient.domain.entity

import ru.paramonov.vknewsclient.R

data class Profile(
    val id: Long = 0,
    val firstName: String = "Nikita",
    val lastName: String = "Paramonov",
    val screenName: String = "vk.com/id2738127",
    val profilePhoto: Int = R.drawable.post_comunity_thumbnail,
    val homeTown: String = "Moscow",
    val bDate: String = "10.11.2002"
)