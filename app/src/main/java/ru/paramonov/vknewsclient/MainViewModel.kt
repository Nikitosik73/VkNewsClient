package ru.paramonov.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.StatisticItem
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    private val initialList = mutableListOf<InstagramModel>().apply {
        repeat(500) {
            add(
                InstagramModel(
                    id = it,
                    title = "Title $it",
                    isFollowed = Random.nextBoolean()
                )
            )
        }
    }

    private val _models = MutableLiveData<List<InstagramModel>>(initialList)
    val models: LiveData<List<InstagramModel>> = _models

    fun changeFollowingStatus(model: InstagramModel) {
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll { instagramModel ->
            if (instagramModel == model) {
                instagramModel.copy(isFollowed = !instagramModel.isFollowed)
            } else {
                instagramModel
            }
        }
        _models.value = modifiedList
    }

    fun updateCount(item: StatisticItem) {
        val currentStatistic = _feedPost.value?.statistics ?: throw IllegalStateException()
        val modifiedStatistic = currentStatistic.toMutableList().apply {
            replaceAll { currentItem ->
                if (currentItem.type == item.type) {
                    currentItem.copy(count = currentItem.count + 1)
                } else {
                    currentItem
                }
            }
        }
        _feedPost.value = _feedPost.value?.copy(statistics = modifiedStatistic)
    }
}