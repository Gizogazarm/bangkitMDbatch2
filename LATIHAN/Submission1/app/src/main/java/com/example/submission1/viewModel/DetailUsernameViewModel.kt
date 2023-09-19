package com.example.submission1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.model.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUsernameViewModel : ViewModel() {

    private lateinit var query: String

    companion object {
        const val TAG = "DetailUsernameViewModel"
    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _nama = MutableLiveData<String>()
    val nama: LiveData<String> = _nama

    private val _avatar = MutableLiveData<String>()
    val avatar: LiveData<String> = _avatar

    private val _followerNumber = MutableLiveData<Int>()
    val followerNumber: LiveData<Int> = _followerNumber

    private val _followingNumber = MutableLiveData<Int>()
    val followingNumber: LiveData<Int> = _followingNumber

    fun setUsername(username: String?) {
        _username.value = username!!
    }

    internal fun getDetailUsername() {
        query = username.value ?: ""
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().getusername(query).execute()
                if (response.isSuccessful) {
                    _avatar.postValue(response.body()?.avatarUrl)
                    _username.postValue(response.body()?.login)
                    _nama.postValue(response.body()?.name)
                    _followerNumber.postValue(response.body()?.followers)
                    _followingNumber.postValue(response.body()?.following)
                } else {
                    Log.e(TAG, "search gagal ${response.message()}")
                }
            } catch (t: Throwable) {
                Log.e(TAG, "Search gagal. Error Message: ${t.message} ")
            }
        }
    }

}