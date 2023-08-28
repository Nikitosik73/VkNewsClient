package ru.paramonov.vknewsclient.data.mapper

import ru.paramonov.vknewsclient.data.network.model.profile.ProfileResponseDto
import ru.paramonov.vknewsclient.domain.entity.Profile
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
}