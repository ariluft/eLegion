package com.pkononov.elegion

import com.pkononov.elegion.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface AcademyApi {
    @POST("registration")
    fun registration(@Body user: OldUser):Completable

    @GET("albums")
    fun getAlbums(): Single<Albums>

    @GET("albums/{id}")
    fun getAlbum(@Path("id") id:Int): Single<Album>

    @GET("songs")
    fun getSongs(): Call<Songs>

    @GET("songs/{id}")
    fun getSong(@Path("id") id:Int): Call<Song>

    @GET("user")
    fun authorization() :Single<Users>
}