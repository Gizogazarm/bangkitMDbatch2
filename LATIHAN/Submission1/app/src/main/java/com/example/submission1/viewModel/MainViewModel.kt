package com.example.submission1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.model.response.ListUsernameResponse
import com.example.submission1.model.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response



class MainViewModel : ViewModel() {

    private lateinit var query: String

    companion object {
        const val TAG = "MainViewModel"
    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _totalCount = MutableLiveData<Int>()
    val totalCount:LiveData<Int> = _totalCount

    private val _listUsername = MutableLiveData<List<ItemsItem>>()
    val listUsername: MutableLiveData<List<ItemsItem>> = _listUsername

    init {
        searchUsername()
    }

    fun setUsername(query: String) {
        _username.value = query
    }



    private fun searchUsername() {
        query = username.value ?: ""
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : retrofit2.Callback<ListUsernameResponse> {
            override fun onResponse(
                call: Call<ListUsernameResponse>,
                response: Response<ListUsernameResponse>
            ) {
                if (response.isSuccessful){
                    _totalCount.value = response.body()?.totalCount
                    _listUsername.value = response.body()?.items
                    Log.d(TAG, "Search successful. Total Count: ${_totalCount.value}, List Size: ${_listUsername.value?.size}")
                } else {

                    Log.e(TAG, "Search unsuccessful. Response Message: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<ListUsernameResponse>, t: Throwable) {

                Log.e(TAG, "Search failed. Error Message: ${t.message}", t)

            }


        })
    }


}