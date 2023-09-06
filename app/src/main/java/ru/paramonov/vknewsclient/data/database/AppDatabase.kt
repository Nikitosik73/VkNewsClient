package ru.paramonov.vknewsclient.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.paramonov.vknewsclient.data.database.dao.NewsFeedDao
import ru.paramonov.vknewsclient.data.database.model.FeedPostDbModel

@Database(entities = [FeedPostDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsFeedDao(): NewsFeedDao

    companion object {

        private const val NAME_DB = "feedPost.db"
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            instance?.let { return it }

            synchronized(LOCK) {
                instance?.let { return it }
                val database = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = NAME_DB
                ).build()
                instance = database
                return database
            }
        }
    }
}