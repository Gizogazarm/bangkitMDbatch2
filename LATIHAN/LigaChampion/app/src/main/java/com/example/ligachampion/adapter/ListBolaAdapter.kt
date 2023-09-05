package com.example.ligachampion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ligachampion.databinding.ItemListBolaBinding
import com.example.ligachampion.dataclass.KlubBola

class ListBolaAdapter(private val listBola: ArrayList<KlubBola>) :
    RecyclerView.Adapter<ListBolaAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnitemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnitemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemListBolaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemListBolaBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBola.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val namaBola = listBola[position].namaBola
        val logoBola = listBola[position].photo
        val jumlahGelar = listBola[position].gelar

        holder.binding.titleKlubBola.text = namaBola
        holder.binding.tvJumlahGelar.text = jumlahGelar
        Glide.with(holder.itemView.context)
            .load(logoBola)
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listBola[holder.bindingAdapterPosition]) }

    }
}

interface OnitemClickCallback {
    fun onItemClicked(data:KlubBola)

}
