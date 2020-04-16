package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName

class Albums(
    @SerializedName("data")
    val data: List<Album.Companion.DataBean>
)

