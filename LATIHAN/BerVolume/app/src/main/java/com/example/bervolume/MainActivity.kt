package com.example.bervolume

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var edtLength: EditText
    private lateinit var edtWidth: EditText
    private lateinit var edtHeight: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnCalculate: Button
    private lateinit var btnIntent: Button

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtLength = findViewById(R.id.edt_length)
        edtWidth = findViewById(R.id.edt_width)
        edtHeight = findViewById(R.id.edt_height)
        tvResult = findViewById(R.id.tv_result)
        btnCalculate = findViewById(R.id.btn_result)
        btnIntent = findViewById(R.id.btn_intent)
        btnCalculate.setOnClickListener(this)
        btnIntent.setOnClickListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT,tvResult.text.toString())
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_result -> {
                val inputLength = edtLength.text.toString().trim()
                val inputWidth = edtWidth.text.toString().trim()
                val inputHeight = edtHeight.text.toString().trim()
                var emptyInput = false
                if (inputLength.isEmpty()) {
                    emptyInput = true
                    edtLength.error = "Field ini tidak boleh kosong"
                }
                if (inputWidth.isEmpty()) {
                    emptyInput = true
                    edtWidth.error = "Field ini tidak boleh kosong"
                }
                if (inputHeight.isEmpty()) {
                    emptyInput = true
                    edtHeight.error = "Field ini tidak boleh kosong"
                }
                if (!emptyInput) {
                    val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                    tvResult.text = volume.toString()
                }
            }
            R.id.btn_intent -> {
                val intent = Intent(this@MainActivity,IntentActivity::class.java)
                startActivity(intent)
            }


        }

    }
}