package com.example.submission1.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.databinding.ItemRowListUserBinding
import com.example.submission1.model.response.ItemsItem


class AdapterListUsername: ListAdapter<ItemsItem,AdapterListUsername.MyViewHolder>(DIFF_CALLBACK){
    private lateinit var onitemClickCallback: OnitemClickCallback

    fun setOnItemClickCallback(onitemClickCallback: OnitemClickCallback){
        this.onitemClickCallback = onitemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class MyViewHolder (private val binding: ItemRowListUserBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(username: ItemsItem) {
            Glide.with(binding.root.context)
                .load(username.avatarUrl)
                .into(binding.profileImage)

            binding.tvUsername.text = username.login

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val username = getItem(position)
        holder.itemView.setOnClickListener { onitemClickCallback.onClickItem(username) }
        holder.bind(username)

    }

    interface OnitemClickCallback {
        fun onClickItem(username: ItemsItem)
    }


}