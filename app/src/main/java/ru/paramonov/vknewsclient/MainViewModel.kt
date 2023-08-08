package ru.paramonov.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.StatisticItem
import ru.paramonov.vknewsclient.ui.NavigationItem

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> = _selectedNavItem

    fun selectNavItem(item: NavigationItem) {
        _selectedNavItem.value = item
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentFeedPost = _feedPosts.value?.toMutableList() ?: mutableListOf()
        val currentStatistics = feedPost.statistics
        val modifiedStatistics = currentStatistics.toMutableList().apply {
            replaceAll { currentItem ->
                if (currentItem.type == item.type) {
                    currentItem.copy(count = currentItem.count + 1)
                } else {
                    currentItem
                }
            }
        }
        val modifiedFeedPost = feedPost.copy(statistics = modifiedStatistics)
        _feedPosts.value = currentFeedPost.apply {
            replaceAll { currentFeedPost ->
                if (currentFeedPost.id == modifiedFeedPost.id) {
                    modifiedFeedPost
                } else {
                    currentFeedPost
                }
            }
        }
    }

    fun deletePost(feedPost: FeedPost) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPost)
        _feedPosts.value = modifiedList
    }
}