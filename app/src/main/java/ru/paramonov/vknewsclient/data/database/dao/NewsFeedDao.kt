package ru.paramonov.vknewsclient.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.paramonov.vknewsclient.data.database.model.FeedPostDbModel

@Dao
interface NewsFeedDao {

    @Query("select * from feed_posts")
    fun getAllNewsFeed(): Flow<List<FeedPostDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedPost(feedPostDbModel: FeedPostDbModel)

    @Query("delete from feed_posts where id = :feedPostId")
    suspend fun removeFeedPost(feedPostId: Long)
}
