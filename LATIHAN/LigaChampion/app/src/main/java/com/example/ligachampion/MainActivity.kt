package com.example.ligachampion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ligachampion.adapter.ListBolaAdapter
import com.example.ligachampion.adapter.OnitemClickCallback
import com.example.ligachampion.databinding.ActivityMainBinding
import com.example.ligachampion.dataclass.KlubBola

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val list = ArrayList<KlubBola>()

    companion object {
        const val KEY_VALUE = "key_value"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBola.setHasFixedSize(true)
        list.addAll(getlistKlubBola())
        showRecycleList()


        binding.btnAbout.setOnClickListener(this)

    }

    private fun showRecycleList() {
        binding.rvBola.layoutManager = LinearLayoutManager(this)
        val listBolaAdapter = ListBolaAdapter(list)
        binding.rvBola.adapter = listBolaAdapter

        listBolaAdapter.setOnItemClickCallback(object: OnitemClickCallback {
            override fun onItemClicked(data: KlubBola) {
                val intent = Intent(this@MainActivity,DetailActivity::class.java)
                intent.putExtra(KEY_VALUE,data)
                startActivity(intent)
            }
        })

    }

    private fun getlistKlubBola(): ArrayList<KlubBola> {

        val klubBola = resources.getStringArray(R.array.data_bola)
        val descriptionBola = resources.getStringArray(R.array.description_bola)
        val gelar = resources.getStringArray(R.array.jumlah_gelar)
        val dataPhoto = resources.getStringArray(R.array.foto_bola)
        val listBola = ArrayList<KlubBola>()

        for (i in klubBola.indices) {
            val bola = KlubBola(klubBola[i], descriptionBola[i], gelar[i], dataPhoto[i])
            listBola.add(bola)
        }
        return listBola

    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_about) {
            val intent = Intent(this@MainActivity,AboutActivity::class.java)
            startActivity(intent)
        }
    }
}