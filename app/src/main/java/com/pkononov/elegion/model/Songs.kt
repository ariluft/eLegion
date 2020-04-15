package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName

class Songs(
    @SerializedName("data")
    val data: List<Song>
)