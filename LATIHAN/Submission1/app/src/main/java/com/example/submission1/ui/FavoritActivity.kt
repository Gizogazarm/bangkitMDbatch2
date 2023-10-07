package com.example.submission1.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.submission1.adapter.AdapterListUsername
import com.example.submission1.databinding.ActivityFavoritBinding
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.model.retrofit.ApiConfig
import com.example.submission1.model.room.FavoriteDatabase
import com.example.submission1.model.room.FavoriteUser
import com.example.submission1.repository.DetailUsernameRepository
import com.example.submission1.viewModel.FavoriteViewModel
import kotlin.collections.map


class FavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var adapter: AdapterListUsername
    private lateinit var items: ArrayList<ItemsItem>
    private lateinit var username: String
    private val apiservice by lazy { ApiConfig.instance }
    private val dao by lazy { FavoriteDatabase.getInstance(application).favoriteDao() }
    private val repository by lazy { DetailUsernameRepository(apiservice,dao) }
    private val viewModel: FavoriteViewModel by viewModelsFactory { FavoriteViewModel(repository) }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterListUsername()

            viewModel.getFavoriteData().observe(this){ users ->
                Log.d("list FavoritUser", "$users")
                items = rvListfavorite(users)
                adapter.submitList(items)
                binding.rvFavorite.adapter = adapter
            }

    }

    private fun rvListfavorite(favoriteUser: List<FavoriteUser>): ArrayList<ItemsItem> {
        val items = arrayListOf<ItemsItem>()
        favoriteUser.map {
            val item = ItemsItem(login = it.username, avatarUrl = it.avatar)
            items.add(item)
        }
        return items
    }

    private fun adapter(listItem: ArrayList<ItemsItem>) {
        adapter = AdapterListUsername()
    }
}


