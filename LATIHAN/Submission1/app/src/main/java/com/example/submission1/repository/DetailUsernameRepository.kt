package com.example.submission1.repository

import android.util.Log
import com.example.submission1.model.response.GetDetailUsernameResponse
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.model.response.ListUsernameResponse
import com.example.submission1.model.retrofit.ApiService

class DetailUsernameRepository(private val apiService: ApiService) {

    private lateinit var username: String
    private lateinit var getDetailUsername: GetDetailUsernameResponse
    private lateinit var usernameFollower: List<ItemsItem>
    private lateinit var usernameFollowing: List<ItemsItem>


    fun getDetailUsername(): GetDetailUsernameResponse {
        val response = apiService.getusername(username).execute()
        try {
            if (response.isSuccessful) {
                getDetailUsername = response.body()!!
            } else {
                Log.e(TAG, "getDetailUsername: error ${response.message()}")
            }
        } catch (t: Throwable) {
            Log.e(TAG, "getDetailUsername: ${t.message} ")
        }

        return getDetailUsername
    }

    fun getFollower(): List<ItemsItem> {
        val response = apiService.getFollowers(username).execute()
        try {
            if (response.isSuccessful) {
                usernameFollower = response.body()!!
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}" )
            }
        }catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} " )
        }
        return usernameFollower
    }

    fun getFollowing(): List<ItemsItem> {
        val response = apiService.getFollowing(username).execute()
        try {
            if (response.isSuccessful) {
                usernameFollowing = response.body()!!
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}" )
            }
        }catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} ")
        }

        return usernameFollowing
    }

    fun setUsername(username: String) {
        this.username = username
    }

    interface Listener {
        fun showMessageError(message: String)
    }

    companion object {
        const val TAG = "DetailUserRepo"
    }

}