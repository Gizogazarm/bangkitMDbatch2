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

    private val _usernameFollower = MutableLiveData<List<ItemsItem>>()
    val usernameFollower: LiveData<List<ItemsItem>> = _usernameFollower

    private val _usernameFollowing = MutableLiveData<List<ItemsItem>>()
    val usernameFollowing: LiveData<List<ItemsItem>> = _usernameFollowing

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private var cachedFollowers: List<ItemsItem>? = null
    private var cachedFollowing: List<ItemsItem>? = null

    fun setUsername(username: String?) {
        _username.value = username!!
    }

    internal fun getDetailUsername() {
        query = username.value ?: ""
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().getusername(query).execute()
                if (response.isSuccessful) {
                    _isLoading.postValue(false)
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

    internal fun getListFollower() {
        if (cachedFollowers != null){
            _usernameFollower.postValue(cachedFollowers!!)
        } else {
            _isLoading.value = true
            query = username.value ?: ""
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = ApiConfig.getApiService().getFollowers(query).execute()
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)
                        cachedFollowers = response.body()
                        _usernameFollower.postValue(response.body())
                        Log.d(TAG, "getListFollower: Berhasil ${response.message()}")
                    } else {
                        Log.e(TAG, "getListFollower: ${response.message()}")
                    }
                } catch (t: Throwable) {
                    Log.e(TAG, "getListFollower: ${t.message}")
                }
            }
        }

    }

    internal fun getListFollowing() {
        if(cachedFollowing != null){
            _usernameFollower.postValue(cachedFollowing!!)
        } else {
            _isLoading.value = true
            query = username.value ?: ""
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = ApiConfig.getApiService().getFollowing(query).execute()
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)
                        cachedFollowing = response.body()
                        _usernameFollowing.postValue(response.body())
                    } else {
                        Log.e(TAG, "getListFollower: ${response.message()}")
                    }
                } catch (t: Throwable) {
                    Log.e(TAG, "getListFollower: ${t.message}")
                }
            }
        }
    }

    fun clearCache() {
        cachedFollowers = null
        cachedFollowing = null
    }

}