package com.example.submission1.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.submission1.adapter.AdapterListUsername
import com.example.submission1.databinding.FragmentFollowBinding
import com.example.submission1.model.response.ItemsItem
import com.example.submission1.viewModel.DetailUsernameViewModel


class FollowFragement : Fragment() {

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private var username = ""
    private var position = 0
    private val detailUsernameViewModel:DetailUsernameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }


        if (position == 1) {
            detailUsernameViewModel.getListFollower(username)
            detailUsernameViewModel.usernameFollower.observe(viewLifecycleOwner){
                showRvList(it)
            }

            detailUsernameViewModel.isLoadingFollow.observe(viewLifecycleOwner){
                showLoading(it)
            }
        } else {
            detailUsernameViewModel.getListFollowing(username)
            detailUsernameViewModel.usernameFollowing.observe(viewLifecycleOwner){
                showRvList(it)
            }

            detailUsernameViewModel.isLoadingFollow.observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
    }

    private fun showRvList(listUser: List<ItemsItem>) {
        val adapter = AdapterListUsername()
        adapter.submitList(listUser)
        binding.rvListFollow.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterListUsername.OnitemClickCallback {
            override fun onClickItem(username: ItemsItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(MainActivity.USERNAME,username.login)
                startActivity(intent)
            }

        })
    }

    private fun showLoading(boolean: Boolean) {
        binding.progressDataFollow.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()

            detailUsernameViewModel.clearCache()

            if (position == 1) {
                detailUsernameViewModel.getListFollower(username)
                detailUsernameViewModel.isLoadingFollow.observe(viewLifecycleOwner){
                    showLoading(it)
                }
            } else {
                detailUsernameViewModel.getListFollowing(username)
                detailUsernameViewModel.isLoadingFollow.observe(viewLifecycleOwner){
                    showLoading(it)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}