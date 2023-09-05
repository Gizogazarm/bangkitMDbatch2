package com.example.ligachampion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.ligachampion.databinding.ActivityDetailBinding
import com.example.ligachampion.dataclass.KlubBola

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBola = intent.getParcelableExtra<KlubBola>(MainActivity.KEY_VALUE)
        val dataNamaClubBola = dataBola?.namaBola
        val dataDescription = dataBola?.description
        val dataPhotoPemain = dataBola?.photoPlayer
        val dataPhotoLogoBola = dataBola?.photo
        val dataNamaPemain = dataBola?.namaPemainBola

        binding.tvKlubBola.text = dataNamaClubBola
        binding.descriptionBola.text = dataDescription
        binding.namaPemainBola.text = dataNamaPemain

        showPhoto(dataPhotoPemain,binding.picPemainBola)
        showPhoto(dataPhotoLogoBola,binding.imageViewBola)
    }

    private fun showPhoto(data: String?, image: ImageView) {
        Glide.with(this)
            .load(data)
            .into(image)
    }
}