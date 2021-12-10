package com.chiki.devbytes.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.chiki.devbytes.database.VideosDatabase.Companion.getDatabase
import com.chiki.devbytes.repository.VideosRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DevByteViewModel(application:Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = VideosRepository(database)

    init {
        viewModelScope.launch {
            try{
                repository.refreshVideos()
            }
            catch (e: Exception){}
        }
    }

    //States
    val playList = repository.videos    //PlayList to show in the RecyclerView

}

class DevByteViewModelFactory(private val application:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevByteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DevByteViewModel(application) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}