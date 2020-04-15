package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName

class Song(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: String
)