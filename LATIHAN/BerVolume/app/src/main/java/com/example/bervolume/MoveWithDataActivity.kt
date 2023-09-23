package com.example.bervolume


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bervolume.databinding.ActivityMoveWithDataBinding


class MoveWithDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoveWithDataBinding

    companion object {
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val name = intent.getStringExtra(EXTRA_NAME)
            val age = intent.getIntExtra(EXTRA_AGE,0)
            val text = "name : $name dan usia : $age"
            tvDataReceived.text= text

        }

    }
}