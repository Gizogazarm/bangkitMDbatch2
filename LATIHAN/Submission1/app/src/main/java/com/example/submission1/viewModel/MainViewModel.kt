package com.example.submission1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.model.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MainViewModel : ViewModel() {

    private lateinit var query: String

    companion object {
        const val TAG = "MainViewModel"
    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private val _listUsername = MutableLiveData<List<ItemsItem>>()
    val listUsername: MutableLiveData<List<ItemsItem>> = _listUsername


    fun setUsername(query: String) {
        _username.value = query
    }


    internal fun searchUsername() {
        _isLoading.value = true
        query = username.value ?: ""
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().searchUsers(query).execute()
                if (response.isSuccessful) {
                    _isLoading.postValue(false)
                    _totalCount.postValue(response.body()?.totalCount)
                    _listUsername.postValue(response.body()?.items)
                    Log.d(TAG, "Search Berhasil")
                } else {
                    Log.e(TAG, "Search Gagal ${response.message()}" )
                }
            } catch (t: Throwable) {
                Log.e(TAG, "Search gagal. Error Message: ${t.message}" )
            }
        }



    }


}