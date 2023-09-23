package com.example.submission1.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission1.ui.FollowFragement

class SectionPagerAdapter(appCompatActivity: AppCompatActivity):FragmentStateAdapter(appCompatActivity) {
    private lateinit var username: String

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getItemCount(): Int {
            return 2
        }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragement()
        fragment.arguments = Bundle().apply {
            putString(FollowFragement.ARG_USERNAME,username)
            putInt(FollowFragement.ARG_POSITION,position+1)
        }
        return fragment
    }

}