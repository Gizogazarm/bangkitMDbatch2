package com.example.submission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.submission1.R
import com.example.submission1.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var nama: String? = null

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


        }



    }

    private fun showSearchView(searchView: SearchView, searchBar: SearchBar) {
        searchView.setupWithSearchBar(searchBar)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            nama = searchView.text.toString().trim().lowercase()
            searchView.hide()
            Log.d(TAG, "$nama")
            false
        }

    }


}