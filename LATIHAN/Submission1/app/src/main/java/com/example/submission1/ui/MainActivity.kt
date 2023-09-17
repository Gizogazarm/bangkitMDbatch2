package com.example.submission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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


            mainViewModel.listUsername.observe(this@MainActivity){
                if(it != null) {
                    Log.d(TAG, "Showing List of Users. Nama: $nama, List Size: ${it.size}")
                    showListUsername(it)
                }
            }

            mainViewModel.isLoading.observe(this@MainActivity) {
                showLoading(it)
            }

            mainViewModel.totalCount.observe(this@MainActivity){
                if(it == 0) {
                    Toast.makeText(this@MainActivity,R.string.not_found,Toast.LENGTH_LONG).show()
                }
            }
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
            mainViewModel.setUsername(nama)
            mainViewModel.searchUsername()
            false
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressData.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}

