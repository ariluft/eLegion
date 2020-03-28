package com.pkononov.elegion

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class SharedPreferencesHelper(context: Context) {

    private var mSharedPreferences: SharedPreferences
    private val mGson = Gson()

    init {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        const val USERS_KEY = "USERS_KEY"
        val USERS_TYPE: Type = object : TypeToken<List<User>>() {}.type
    }

    fun getUsers(): ArrayList<User> {
        val users: ArrayList<User> =
            mGson.fromJson(mSharedPreferences.getString(USERS_KEY, ""), USERS_TYPE)
                ?: return ArrayList()
        return users
    }

    fun addUser(user: User): Boolean {
        val users = getUsers()
        users.forEach {
            if (it.login.toLowerCase(Locale.ROOT) == user.login.toLowerCase(Locale.ROOT)) {
                return false
            }
        }

        users.add(user)
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users)).apply()
        return true
    }

    fun login(login:String, password:String): User? {
        var users = getUsers()
        users.forEach {
            if (login.toLowerCase() == it.login.toLowerCase() &&
                password == it.password
            ) {
                it.setHasSuccessLogin()
                mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE))
                    .apply()
                return it
            }
        }
        return null
    }

    fun  getSuccessLogin(): Array<String> {
        return getUsers().map { it.login }.toTypedArray()
    }
}