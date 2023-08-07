package ru.paramonov.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> = _isFollowing

    fun changeFollow() {
        val currentFollow = _isFollowing.value ?: false
        _isFollowing.value = !currentFollow
    }

    fun updateCount(item: StatisticItem) {
        val currentStatistic = _feedPost.value?.statistics ?: throw IllegalStateException()
        val newStatistic = currentStatistic.toMutableList().apply {
            replaceAll { currentItem ->
                if (currentItem.type == item.type) {
                    currentItem.copy(count = currentItem.count + 1)
                } else {
                    currentItem
                }
            }
        }
        _feedPost.value = _feedPost.value?.copy(statistics = newStatistic)
    }
}