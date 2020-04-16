package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Album(
    @SerializedName("data")
    var mData:DataBean
) {
    companion object {
        class DataBean : Serializable{
            @SerializedName("id")
            var id: Int = 0
            @SerializedName("name")
            lateinit var name: String
            @SerializedName("release_date")
            lateinit var release_date: String
            @SerializedName("songs")
            lateinit var songs: List<Song>
        }
    }
}