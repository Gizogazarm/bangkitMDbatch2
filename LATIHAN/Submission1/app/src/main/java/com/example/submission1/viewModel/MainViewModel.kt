package com.example.submission1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.repository.MainViewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val repository: MainViewRepository) : ViewModel() {


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


    fun searchUsername() {
        _isLoading.value = true
        repository.setUsername(_username.value!!)
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchUsername()
             _listUsername.postValue(repository.getListItem())
            _totalCount.postValue(repository.getTotalCount())
            _isLoading.postValue(false)
        }


    }


}