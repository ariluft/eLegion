package com.pkononov.elegion

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(@SerializedName("email") var email: String,
           @SerializedName("name") var name: String,
           @SerializedName("password") var password: String
) : Serializable{
    private var mHasSuccessLogin = false


    fun hasSuccessLogin() : Boolean{
        return mHasSuccessLogin
    }

    fun setHasSuccessLogin(){
        mHasSuccessLogin = true
    }
}