package com.pkononov.elegion

import java.io.Serializable

class User(var login:String, var password:String) : Serializable{
    private var mHasSuccessLogin = false

    fun hasSuccessLogin() : Boolean{
        return mHasSuccessLogin
    }

    fun setHasSuccessLogin(){
        mHasSuccessLogin = true
    }
}