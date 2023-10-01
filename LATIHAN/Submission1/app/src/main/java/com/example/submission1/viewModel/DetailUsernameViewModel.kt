package com.example.submission1.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.model.response.GetDetailUsernameResponse
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.repository.DetailUsernameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUsernameViewModel(private val repository: DetailUsernameRepository) : ViewModel() {

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

    private val _isLoadingDetail = MutableLiveData<Boolean>().apply { value = false }
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _isLoadingFollow = MutableLiveData<Boolean>().apply { value = false }
    val isLoadingFollow: LiveData<Boolean> = _isLoadingFollow

    private lateinit var getDetailUsernameResponse: GetDetailUsernameResponse

    private var cachedFollowers: List<ItemsItem>? = null
    private var cachedFollowing: List<ItemsItem>? = null

    fun setUsername(username: String?) {
        _username.value = username!!
    }

    fun getDetailUsername() {
        _isLoadingDetail.value = true
        repository.setUsername(_username.value!!)
        viewModelScope.launch(Dispatchers.IO) {
            getDetailUsernameResponse = repository.getDetailUsername()
            _username.postValue(getDetailUsernameResponse.login)
            _nama.postValue(getDetailUsernameResponse.name)
            _avatar.postValue(getDetailUsernameResponse.avatarUrl)
            _followerNumber.postValue(getDetailUsernameResponse.followers)
            _followingNumber.postValue(getDetailUsernameResponse.following)
            _isLoadingDetail.postValue(false)
        }

    }

    fun getListFollower() {
        if (cachedFollowers != null) {
            _usernameFollower.postValue(cachedFollowers!!)
        } else {
            _isLoadingFollow.value = true
            repository.setUsername(_username.value!!)
            viewModelScope.launch(Dispatchers.IO) {
                repository.getFollower()
                _usernameFollower.postValue(repository.getListUsernameFollower())
                cachedFollowers = repository.getListUsernameFollower()
                _isLoadingFollow.postValue(false)
            }

        }

    }

    fun getListFollowing() {
        if (cachedFollowing != null) {
            _usernameFollowing.postValue(cachedFollowing!!)
        } else {
            _isLoadingFollow.value = true
            viewModelScope.launch(Dispatchers.IO) {
                repository.getFollowing()
                _usernameFollowing.postValue(repository.getListUsernameFollowing())
                cachedFollowing = repository.getListUsernameFollowing()
                _isLoadingFollow.postValue(false)

            }
        }
    }

    fun clearCache() {
        cachedFollowers = null
        cachedFollowing = null
    }

}