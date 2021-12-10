package com.chiki.devbytes.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chiki.devbytes.database.VideosDatabase.Companion.getDatabase
import com.chiki.devbytes.repository.VideosRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext,params) {
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        return try{
            repository.refreshVideos()
            Result.success()
        }catch (exception: HttpException){
            Result.retry()
        }
    }
    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }
}