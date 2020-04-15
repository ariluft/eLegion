package com.pkononov.elegion.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OldUser(
    @field:SerializedName("email") var email: String,
    @field:SerializedName("name") var name: String,
    @field:SerializedName(
        "password"
    ) var password: String
) :
    Serializable