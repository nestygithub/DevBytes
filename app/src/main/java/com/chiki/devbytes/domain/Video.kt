package com.chiki.devbytes.domain

import com.chiki.devbytes.smartTruncate

data class Video(
    val title: String,
    val description: String,
    val url: String,
    val updated:String,
    val thumbnail: String) {
    val shortDescription get() =description.smartTruncate(200)
}