package com.example.submission1.model.retrofit

import com.example.submission1.model.response.ListUsernameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<ListUsernameResponse>
}