package com.example.submission1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailUsernameViewModel:ViewModel() {

    private val _username = MutableLiveData<String>()
    val username:LiveData<String> = _username

    private val _nama = MutableLiveData<String>()
    val nama : LiveData<String> = _nama

    private val _avatar = MutableLiveData<String>()
    val avatar: LiveData<String> = _avatar

}