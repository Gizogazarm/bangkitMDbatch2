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

    private val _onError = MutableLiveData<Boolean>().apply { value = false }
    val onError: LiveData<Boolean> = _onError

    private val _onErrorFollow = MutableLiveData<Boolean>().apply { value = false }
    private val _onErrorMsgFollow = MutableLiveData<String>()

    private val _onErrorMsg = MutableLiveData<String>()
    val onErrorMsg: LiveData<String> = _onErrorMsg

    private var getDetailUsernameResponse: GetDetailUsernameResponse? = null
    private var cachedFollowers: List<ItemsItem>? = null
    private var cachedFollowing: List<ItemsItem>? = null
    private lateinit var query: String

    fun setUsername(username: String?) {
        query = username!!
    }

    fun insertFavUser() {
        val username = getDetailUsernameResponse!!.login
        val avatarUrl = getDetailUsernameResponse!!.avatarUrl
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoritUser(username,avatarUrl)
        }
    }

    fun getDetailUsername() {
        _isLoadingDetail.value = true
        repository.setUsername(query)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDetailUsername(object : DetailUsernameRepository.Listener {
                override fun showMessageError(message: String) {
                    _onError.postValue(true)
                    _onErrorMsg.postValue(message)
                }

                override fun result(source: Any?) {
                    getDetailUsernameResponse = source as GetDetailUsernameResponse
                    _username.postValue(getDetailUsernameResponse!!.login)
                    _nama.postValue(getDetailUsernameResponse!!.name)
                    _avatar.postValue(getDetailUsernameResponse!!.avatarUrl)
                    _followerNumber.postValue(getDetailUsernameResponse!!.followers)
                    _followingNumber.postValue(getDetailUsernameResponse!!.following)
                    _isLoadingDetail.postValue(false)
                }
            })


        }

    }

    fun getListFollower() {
        if (cachedFollowers != null) {
            _usernameFollower.postValue(cachedFollowers!!)
        } else {
            _isLoadingFollow.value = true
            viewModelScope.launch(Dispatchers.IO) {
                repository.getFollower(object : DetailUsernameRepository.Listener {
                    override fun showMessageError(message: String) {
                        _onErrorFollow.postValue(true)
                        _onErrorMsgFollow.postValue(message)
                    }

                    override fun result(source: Any?) {
                        cachedFollowers = source as List<ItemsItem>
                        _usernameFollower.postValue(cachedFollowers!!)
                        _isLoadingFollow.postValue(false)
                    }

                })


            }

        }

    }

    fun getListFollowing() {
        if (cachedFollowing != null) {
            _usernameFollowing.postValue(cachedFollowing!!)
        } else {
            _isLoadingFollow.value = true
            viewModelScope.launch(Dispatchers.IO) {
                repository.getFollowing(object : DetailUsernameRepository.Listener {
                    override fun showMessageError(message: String) {
                        _onErrorMsgFollow.postValue(message)
                        _onErrorFollow.postValue(true)
                    }

                    override fun result(source: Any?) {
                        cachedFollowing = source as List<ItemsItem>
                        _usernameFollowing.postValue(cachedFollowing!!)
                        _isLoadingFollow.postValue(false)
                    }

                })


            }
        }
    }

    fun clearCache() {
        cachedFollowers = null
        cachedFollowing = null
    }

}