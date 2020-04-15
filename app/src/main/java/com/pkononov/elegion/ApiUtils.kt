package com.pkononov.elegion

import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtils {
    companion object {
        private var okHttpClient: OkHttpClient? = null
        private var retrofit: Retrofit? = null
        private var gson: Gson? = null
        private var api: AcademyApi? = null

        fun getBasicAuthClient(
            email: String,
            password: String,
            newInstance: Boolean
        ): OkHttpClient {
            if (newInstance || okHttpClient == null) {
                var builder = OkHttpClient.Builder()
                    .followRedirects(false)
                    .followSslRedirects(false)

                builder.authenticator(object : Authenticator {
                    override fun authenticate(route: Route?, response: Response): Request? {
                        val credentials = Credentials.basic(email, password)
                        return response.request.newBuilder().header("Autorization", credentials)
                            .build()
                    }
                })

                builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                okHttpClient = builder.build()
            }

            return okHttpClient!!
        }

        fun getRetrofit(): Retrofit {
            if (gson == null) {
                gson = Gson()
            }

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    //need for interceptors
                    .client(getBasicAuthClient("", "", false))
                    .addConverterFactory(GsonConverterFactory.create(gson!!))
                    .build()
            }

            return retrofit!!
        }

        fun getApi():AcademyApi{
            if (api == null){
                api = getRetrofit().create(AcademyApi::class.java)
            }
            return api!!
        }
    }
}