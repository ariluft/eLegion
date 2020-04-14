package com.pkononov.elegion

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

class ApiUtils {
    companion object{
        private var okHttpClient: OkHttpClient? = null
        fun getBasicAuthClient(emalil:String, password:String, newInstance: Boolean):OkHttpClient{
            if (newInstance || okHttpClient == null){
                var builder = OkHttpClient.Builder()

                builder.authenticator(object:Authenticator{
                    override fun authenticate(route: Route?, response: Response): Request? {
                        val credentials = Credentials.basic(emalil, password)
                        return response.request.newBuilder().header("Autorization", credentials).build()
                    }

                })

                builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                okHttpClient = builder.build()
            }

            return okHttpClient!!
        }
    }
}