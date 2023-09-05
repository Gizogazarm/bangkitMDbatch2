package com.example.ligachampion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val dataLogoBola = dataBola?.namaBola
        val dataDescription = dataBola?.description

        binding.tvKlubBola.text = dataLogoBola
        binding.descriptionBola.text = dataDescription


        Glide.with(this)
            .load(dataBola?.photo)
            .into(binding.imageViewBola)
    }
}