package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName

class Album(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("songs_count")
    val songs_count: Int
)