package com.example.submission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.submission1.R
import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.viewModel.DetailUsernameViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var getUsername: String? = ""
    private val detailUsernameViewModel: DetailUsernameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            getUsername = intent.getStringExtra(MainActivity.USERNAME)
            detailUsernameViewModel.setUsername(getUsername)

            detailUsernameViewModel.username.observe(this@DetailActivity) {
                tvDetailUsername.text = it
            }

            detailUsernameViewModel.avatar.observe(this@DetailActivity){
                Glide.with(this@DetailActivity)
                    .load(it)
                    .into(fotoProfile)
            }

            detailUsernameViewModel.nama.observe(this@DetailActivity){
                tvDetailNama.text = it
            }

            detailUsernameViewModel.followerNumber.observe(this@DetailActivity) {
                tvFollower.text = resources.getString(R.string.follower_count,it)
            }

            detailUsernameViewModel.followingNumber.observe(this@DetailActivity) {
                tvFollowing.text = resources.getString(R.string.following_count,it)
            }

            detailUsernameViewModel.getDetailUsername()

        }





    }
}