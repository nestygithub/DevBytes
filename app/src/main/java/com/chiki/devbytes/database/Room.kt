package com.chiki.devbytes.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {

    @Query("SELECT * FROM databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

@Database(entities = [DatabaseVideo::class],version = 1)
abstract class VideosDatabase: RoomDatabase() {
    abstract val videoDao: VideoDao

    companion object{
        @Volatile
        private var INSTANCE: VideosDatabase? = null

        fun getDatabase(context: Context): VideosDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(context.applicationContext, VideosDatabase::class.java,"dev_bytes_videos_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}