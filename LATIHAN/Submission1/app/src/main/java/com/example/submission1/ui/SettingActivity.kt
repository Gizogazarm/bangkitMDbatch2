package com.example.submission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.submission1.databinding.ActivitySettingBinding
import com.example.submission1.model.retrofit.ApiConfig
import com.example.submission1.preference.SettingPreference
import com.example.submission1.preference.dataStore
import com.example.submission1.repository.MainViewRepository
import com.example.submission1.viewModel.MainViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val apiService by lazy {ApiConfig.instance}
    private val repository by lazy { MainViewRepository(apiService) }
    private val setPref by lazy { SettingPreference.getInstance(application.dataStore) }
    private val viewModel:MainViewModel by viewModelsFactory { MainViewModel(repository,setPref) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) {
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isCheked: Boolean ->
            viewModel.saveThemeSetting(isCheked)
        }

    }
}