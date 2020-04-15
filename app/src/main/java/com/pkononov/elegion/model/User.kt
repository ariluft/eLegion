package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(@SerializedName("id") var id: Int,
           @SerializedName("name") var name: String,
           @SerializedName("email") var email: String
)