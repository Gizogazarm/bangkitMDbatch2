package com.example.submission1.repository

import android.util.Log
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.model.retrofit.ApiService

class MainViewRepository (private val apiService: ApiService) {

    private lateinit var username: String
    private var totalCount: Int = 0
    private lateinit var listItem: List<ItemsItem>



    fun setUsername(username: String) {
        this.username = username
    }

    fun searchUsername() {
        val response = apiService.searchUsers(username).execute()
        try {
            if (response.isSuccessful) {
                totalCount = response.body()!!.totalCount
                listItem = response.body()!!.items
                Log.d(TAG, "searchUsername: berhasil")
            } else {
                Log.e(TAG, "searchUsername: gagal , kode ${response.message()}" )
            }
        }  catch (t : Throwable) {
            Log.e(TAG, "searchUsername: error message = ${t.message}" )
        }
    }

    fun getListItem(): List<ItemsItem> {
        return this.listItem
    }

    fun getTotalCount() : Int {
        return this.totalCount
    }

    companion object {
        const val TAG = "REPOSITORY"
    }


}