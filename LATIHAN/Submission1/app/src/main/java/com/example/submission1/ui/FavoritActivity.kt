package com.example.submission1.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private val apiservice by lazy { ApiConfig.instance }
    private val dao by lazy { FavoriteDatabase.getInstance(application).favoriteDao() }
    private val repository by lazy { DetailUsernameRepository(apiservice, dao) }
    private val viewModel: FavoriteViewModel by viewModelsFactory { FavoriteViewModel(repository) }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getFavoriteData().observe(this) { users ->
            Log.d("list FavoritUser", "$users")
            items = rvListfavorite(users)
            adapterRvList(items)
        }

        viewModel.onLoading.observe(this) {
            showLoading(it)
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

    private fun adapterRvList(listItem: ArrayList<ItemsItem>) {
        adapter = AdapterListUsername()
        adapter.submitList(listItem)
        binding.rvFavorite.adapter = adapter
        viewModel.setFavorit(false)

        adapter.setOnItemClickCallback(object : AdapterListUsername.OnitemClickCallback {
            override fun onClickItem(username: ItemsItem) {
                selectedItemData(username)
            }

        })
    }

    private fun selectedItemData(username: ItemsItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(MainActivity.USERNAME, username.login)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressFavorite.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

