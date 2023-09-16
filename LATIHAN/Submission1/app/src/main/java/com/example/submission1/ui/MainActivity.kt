package com.example.submission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.R
import com.example.submission1.adapter.AdapterListUsername
import com.example.submission1.databinding.ActivityMainBinding
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.viewModel.MainViewModel
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var nama: String = ""
    private val mainViewModel: MainViewModel by viewModels()


    companion object {
        const val TAG = "tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            showSearchView(searchView,searchBar)
            searchView.inflateMenu(R.menu.menu_setting)

            mainViewModel.setUsername(nama)

            mainViewModel.listUsername.observe(this@MainActivity){
                if(it != null) {
                    Log.d(TAG, "Showing List of Users. Nama: $nama, List Size: ${it.size}")
                    showListUsername(it)
                }

            }

            val layoutManager = LinearLayoutManager(this@MainActivity)
            rvListUsername.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@MainActivity,layoutManager.orientation)
            rvListUsername.addItemDecoration(itemDecoration)

        }
    }

    private fun showListUsername(listUser: List<ItemsItem>) {

        val adapter = AdapterListUsername()
        adapter.submitList(listUser)
        binding.rvListUsername.adapter = adapter

    }

    private fun showSearchView(searchView: SearchView, searchBar: SearchBar) {
        searchView.setupWithSearchBar(searchBar)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            nama = searchView.text.toString().trim().lowercase()
            searchView.hide()
            Log.d(TAG, nama)
            false
        }

    }


}

