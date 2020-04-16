package com.pkononov.elegion

import com.pkononov.elegion.model.*
import retrofit2.Call
import retrofit2.http.*

interface AcademyApi {
    @POST("registration")
    fun registration(@Body user: OldUser):Call<Void>

    @GET("albums")
    fun getAlbums(): Call<Albums>

    @GET("albums/{id}")
    fun getAlbum(@Path("id") id:Int): Call<Album>

    @GET("songs")
    fun getSongs(): Call<Songs>

    @GET("songs/{id}")
    fun getSong(@Path("id") id:Int): Call<Song>

    @GET("user")
    fun authorization() :Call<Users>
}