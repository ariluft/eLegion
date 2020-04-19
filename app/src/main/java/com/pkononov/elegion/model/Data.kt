package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName

class Data<T>{
    @SerializedName("data")
    lateinit var response: Any
}