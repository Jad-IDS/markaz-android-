package com.ids.inpoint.utils


import com.google.gson.GsonBuilder

import com.ids.markaz.controller.MyApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClientAuth {
    var BASE_URL ="http://78.46.188.93:4900/api/"
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(requestHeader)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

    private
    val requestHeader: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor{ it.proceed(it.request().newBuilder()
                .addHeader("Authorization", "Bearer "+MyApplication.responseLogin.accessToken.toString())
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build())}
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build()

    private fun cancelRequest() {}


}
