package com.example.submission1.repository

import android.util.Log
import com.example.submission1.model.response.GetDetailUsernameResponse
import com.example.submission1.model.retrofit.ApiService
import com.example.submission1.model.room.FavoriteUser
import com.example.submission1.model.room.FavoriteUserDao

class DetailUsernameRepository(private val apiService: ApiService, private val favoriteUserDao: FavoriteUserDao) {

    private lateinit var username: String

    fun getDetailUsername(listener: Listener) {
        val response = apiService.getusername(username).execute()
        try {
            if (response.isSuccessful) {
               val result = response.body()
                if ( result is GetDetailUsernameResponse) {
                    listener.result(result)
                }
            } else {
                Log.e(TAG, "getDetailUsername: error ${response.message()}")
                listener.showMessageError(ERROR)
            }
        } catch (t: Throwable) {
            Log.e(TAG, "getDetailUsername: ${t.message} ")
            listener.showMessageError(ERROR)
        }
    }

    suspend fun insertFavoritUser(username: String, avatarUrl: String) {
        val data = FavoriteUser(null,username , avatarUrl)
        favoriteUserDao.insertFavUser(data)
    }

    fun getFollower(listener: Listener) {
        val response = apiService.getFollowers(username).execute()
        try {
            if (response.isSuccessful) {
                listener.result(response.body())
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}" )
                listener.showMessageError(ERROR)
            }
        }catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} " )
            listener.showMessageError(ERROR)
        }

    }

    fun getFollowing(listener: Listener) {
        val response = apiService.getFollowing(username).execute()
        try {
            if (response.isSuccessful) {
                listener.result(response.body())
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}" )
                listener.showMessageError(ERROR)
            }

        }catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} ")
            listener.showMessageError(ERROR)
        }


    }

    fun setUsername(username: String) {
        this.username = username
    }


    interface Listener {
        fun showMessageError(message: String)
        fun result(source:Any?)
    }

    companion object {
        const val TAG = "DetailUserRepo"
        const val ERROR = "Data gagal dimuat"
    }

}