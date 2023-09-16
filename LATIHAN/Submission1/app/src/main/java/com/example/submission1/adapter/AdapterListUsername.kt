package com.example.submission1.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.databinding.ItemRowListUserBinding
import com.example.submission1.model.response.ListUsernameResponse

class AdapterListUsername: ListAdapter<ListUsernameResponse,AdapterListUsername.MyViewHolder>(DIFF_CALLBACK){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListUsernameResponse>(){
            override fun areItemsTheSame(
                oldItem: ListUsernameResponse,
                newItem: ListUsernameResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListUsernameResponse,
                newItem: ListUsernameResponse
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class MyViewHolder (private val binding: ItemRowListUserBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(username: ListUsernameResponse) {
            Glide.with(binding.root.context)
                .load(username.items[adapterPosition].avatarUrl)
                .into(binding.profileImage)

            binding.tvUsername.text = username.items[adapterPosition].login

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val username = getItem(position)
        holder.bind(username)

    }

}