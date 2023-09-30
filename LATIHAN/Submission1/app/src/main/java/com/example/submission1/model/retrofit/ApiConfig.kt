package com.example.submission1.model.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {

    private const val BASE_URL = "https://api.github.com"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }

    val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "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")
            .build()
        chain
            .proceed(requestHeaders)
    }

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(authInterceptor)
        .build()



    /*companion object {
        fun getApiService(): ApiService {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "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")
                    .build()
                chain
                    .proceed(requestHeaders)
            }

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(
                ApiService::
                class.java
            )
        }
    }*/
}