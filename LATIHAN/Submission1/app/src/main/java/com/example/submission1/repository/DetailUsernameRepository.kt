package com.example.submission1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.submission1.model.response.GetDetailUsernameResponse
import com.example.submission1.model.retrofit.ApiService
import com.example.submission1.model.room.FavoriteUser
import com.example.submission1.model.room.FavoriteUserDao

class DetailUsernameRepository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
) {


    fun getDetailUsername(username: String, listener: Listener) {
        val response = apiService.getusername(username).execute()
        try {
            if (response.isSuccessful) {
                val result = response.body()
                if (result is GetDetailUsernameResponse) {
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
        val data = FavoriteUser(username, avatarUrl)
        favoriteUserDao.insertFavUser(data)
    }

    suspend fun deleteFavoritUser(username: String, avatarUrl: String) {
        val data = FavoriteUser(username, avatarUrl)
        favoriteUserDao.deleteFavoriteByUsername(data)
    }


    fun getFollower(listener: Listener, username: String) {
        val response = apiService.getFollowers(username).execute()
        try {
            if (response.isSuccessful) {
                listener.result(response.body())
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}")
                listener.showMessageError(ERROR)
            }
        } catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} ")
            listener.showMessageError(ERROR)
        }
    }

    fun getFavUser(username: String): LiveData<FavoriteUser> {
        return favoriteUserDao.getFavoriteByUsername(username)
    }


    fun getAllList(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getAllList()
    }

    fun getFollowing(listener: Listener, username: String) {
        val response = apiService.getFollowing(username).execute()
        try {
            if (response.isSuccessful) {
                listener.result(response.body())
            } else {
                Log.e(TAG, "getFollower: error ${response.message()}")
                listener.showMessageError(ERROR)
            }

        } catch (t: Throwable) {
            Log.e(TAG, "getFollower: error ${response.message()} ")
            listener.showMessageError(ERROR)
        }
    }

    interface Listener {
        fun showMessageError(message: String)
        fun result(source: Any?)
    }

    companion object {
        const val TAG = "DetailUserRepo"
        const val ERROR = "Data gagal dimuat"
    }

}