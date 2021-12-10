package com.chiki.devbytes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.chiki.devbytes.database.VideosDatabase
import com.chiki.devbytes.database.asDomainModel
import com.chiki.devbytes.domain.Video
import com.chiki.devbytes.network.Network
import com.chiki.devbytes.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database:VideosDatabase) {

    val videos:LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()){
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO){
            val playlist = Network.devBytes.getPlayList().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}