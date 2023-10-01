package com.example.submission1.repository

import android.util.Log
import com.example.submission1.model.response.GetDetailUsernameResponse
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.model.retrofit.ApiService

class DetailUsernameRepository(private val apiService: ApiService) {

    private lateinit var username: String
    private var getDetailUsername: GetDetailUsernameResponse? = null
    private var usernameFollower: List<ItemsItem>? = null
    private var usernameFollowing: List<ItemsItem>? = null


    fun getDetailUsername(listener: Listener): GetDetailUsernameResponse? {
        val response = apiService.getusername(username).execute()
        try {
            if (response.isSuccessful) {
                getDetailUsername = response.body()
            } else {
                Log.e(TAG, "getDetailUsername: error ${response.message()}")
                listener.showMessageError(ERROR)
            }
        } catch (t: Throwable) {
            Log.e(TAG, "getDetailUsername: ${t.message} ")
            listener.showMessageError(ERROR)
        }

        return getDetailUsername
    }

    fun getFollower(listener: Listener): List<ItemsItem>? {
        val response = apiService.getFollowers(username).execute()
        try {
            if (response.isSuccessful) {
                usernameFollower = response.body()
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}" )
                listener.showMessageError(ERROR)
            }
        }catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} " )
            listener.showMessageError(ERROR)
        }
        return usernameFollower
    }

    fun getFollowing(listener: Listener): List<ItemsItem>? {
        val response = apiService.getFollowing(username).execute()
        try {
            if (response.isSuccessful) {
                usernameFollowing = response.body()
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}" )
                listener.showMessageError(ERROR)
            }

        }catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} ")
            listener.showMessageError(ERROR)
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
        const val ERROR = "Data gagal dimuat"
    }

}