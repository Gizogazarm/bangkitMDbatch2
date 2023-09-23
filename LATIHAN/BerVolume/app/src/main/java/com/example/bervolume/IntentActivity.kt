package com.example.bervolume

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bervolume.databinding.ActivityIntentBinding


class IntentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var intentBinding: ActivityIntentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentBinding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(intentBinding.root)

        intentBinding.btnMoveActivity.setOnClickListener(this)
        intentBinding.btnMoveActivityData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_move_activity-> {
                val intent = Intent(this@IntentActivity,MovingActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_move_activity_data -> {

                val intent = Intent(this@IntentActivity,MoveWithDataActivity::class.java)
                intent.putExtra(MoveWithDataActivity.EXTRA_NAME,"kukgeruh")
                intent.putExtra(MoveWithDataActivity.EXTRA_AGE,10)
                startActivity(intent)

            }
        }
    }
}