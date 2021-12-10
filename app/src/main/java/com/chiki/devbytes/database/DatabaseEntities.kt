package com.chiki.devbytes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chiki.devbytes.domain.Video

@Entity
data class DatabaseVideo(
    val title: String,
    val description: String,
    @PrimaryKey
    val url: String,
    val updated:String,
    val thumbnail: String)

fun List<DatabaseVideo>.asDomainModel():List<Video>{
    return map {
        Video(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
}