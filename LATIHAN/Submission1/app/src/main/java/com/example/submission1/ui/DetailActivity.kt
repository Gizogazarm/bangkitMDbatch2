package com.example.submission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission1.R
import com.example.submission1.adapter.SectionPagerAdapter
import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.viewModel.DetailUsernameViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private var getUsername: String? = ""
    private lateinit var fragmentState : SectionPagerAdapter
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
            fragmentState = SectionPagerAdapter(this@DetailActivity)
            fragmentState.setUsername(getUsername!!)
            viewPager.adapter = fragmentState
            tabShowOff(tabs,viewPager)
        }





    }

    private fun tabShowOff(tabs: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabs,viewPager) { tab, positon ->
            tab.text = resources.getString(TAB_TITLES[positon])
            tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this@DetailActivity, R.color.black))
        }.attach()
    }
}