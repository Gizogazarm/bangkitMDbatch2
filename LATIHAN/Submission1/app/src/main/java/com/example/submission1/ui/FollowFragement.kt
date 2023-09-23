package com.example.submission1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    private val detailUsernameViewModel: DetailUsernameViewModel by viewModels()

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

        detailUsernameViewModel.setUsername(username)

        if (position == 1) {
            detailUsernameViewModel.getListFollower()
            detailUsernameViewModel.usernameFollower.observe(viewLifecycleOwner){
                showRvList(it)
            }

            detailUsernameViewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        } else {
            detailUsernameViewModel.getListFollowing()
            detailUsernameViewModel.usernameFollowing.observe(viewLifecycleOwner){
                showRvList(it)
            }

            detailUsernameViewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
    }

    private fun showRvList(listUser: List<ItemsItem>) {
        val adapter = AdapterListUsername()
        adapter.submitList(listUser)
        binding.rvListFollow.adapter = adapter
    }

    private fun showLoading(boolean: Boolean) {
        binding.progressDataFollow.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        if (position == 1) {
            detailUsernameViewModel.clearCache()
            detailUsernameViewModel.getListFollower()
            detailUsernameViewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        } else {
            detailUsernameViewModel.clearCache()
            detailUsernameViewModel.getListFollowing()
            detailUsernameViewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}