package com.example.submission1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission1.R
import com.example.submission1.adapter.SectionPagerAdapter
import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.model.retrofit.ApiConfig
import com.example.submission1.repository.DetailUsernameRepository
import com.example.submission1.viewModel.DetailUsernameViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )

        private const val DELAYMILIS: Long = 5000
    }

    private lateinit var binding: ActivityDetailBinding
    private var getUsername: String? = ""
    private lateinit var fragmentState: SectionPagerAdapter
    private val apiService by lazy { ApiConfig.instance }
    private val repository by lazy { DetailUsernameRepository(apiService) }
    private val detailUsernameViewModel: DetailUsernameViewModel by viewModelsFactory {
        DetailUsernameViewModel(
            repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            getUsername = intent.getStringExtra(MainActivity.USERNAME)
            detailUsernameViewModel.setUsername(getUsername)

            detailUsernameViewModel.username.observe(this@DetailActivity) {
                tvDetailUsername.text = it
            }

            detailUsernameViewModel.avatar.observe(this@DetailActivity) {
                Glide.with(this@DetailActivity)
                    .load(it)
                    .into(fotoProfile)
            }

            detailUsernameViewModel.nama.observe(this@DetailActivity) {
                tvDetailNama.text = it
            }

            detailUsernameViewModel.isLoadingDetail.observe(this@DetailActivity) {
                showLoading(it)
            }

            detailUsernameViewModel.followerNumber.observe(this@DetailActivity) {
                tvFollower.text = resources.getString(R.string.follower_count, it)
            }

            detailUsernameViewModel.followingNumber.observe(this@DetailActivity) {
                tvFollowing.text = resources.getString(R.string.following_count, it)
            }

            detailUsernameViewModel.onError.observe(this@DetailActivity) {
                handleError(it)
            }

            detailUsernameViewModel.getDetailUsername()
            fragmentState = SectionPagerAdapter(this@DetailActivity)
            fragmentState.setUsername(getUsername!!)
            viewPager.adapter = fragmentState
            tabShowOff(tabs, viewPager)
        }


    }

    private fun handleError(onError: Boolean) {
        if (onError) {
            detailUsernameViewModel.onErrorMsg.observe(this) {
                showMesaggeError(it)
            }
        }
    }

    private fun showMesaggeError(message: String) {

        val handler = Handler(Looper.getMainLooper())
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAYMILIS)

    }

    private fun tabShowOff(tabs: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabs, viewPager) { tab, positon ->
            tab.text = resources.getString(TAB_TITLES[positon])
            tabs.setSelectedTabIndicatorColor(
                ContextCompat.getColor(
                    this@DetailActivity,
                    R.color.black
                )
            )
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}