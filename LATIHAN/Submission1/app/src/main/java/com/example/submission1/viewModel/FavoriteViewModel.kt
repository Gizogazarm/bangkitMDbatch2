package com.example.submission1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.model.room.FavoriteUser
import com.example.submission1.repository.DetailUsernameRepository

class FavoriteViewModel(private val repository: DetailUsernameRepository): ViewModel() {

    private var _onLoading = MutableLiveData<Boolean>().apply { value = false }
    val onLoading: LiveData<Boolean> = _onLoading

    fun setFavorit(boolean: Boolean) {
        _onLoading.value = boolean
    }


    fun getFavoriteData(): LiveData<List<FavoriteUser>> {
        _onLoading.value = true
        return repository.getAllList()
    }
}