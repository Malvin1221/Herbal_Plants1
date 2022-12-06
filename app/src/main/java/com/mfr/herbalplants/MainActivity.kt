package com.mfr.herbalplants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.mfr.herbalplants.viewMinuman.AdminMinumanActivity
import com.mfr.herbalplants.viewTanaman.AdminTanamanActivity

class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTanaman = findViewById<Button>(R.id.btn_Tanaman)
        btnTanaman.setOnClickListener{
            val intent = Intent(this, AdminTanamanActivity::class.java)
            startActivity(intent)
        }
        val btnMinuman = findViewById<Button>(R.id.btn_Minuman)
        btnMinuman.setOnClickListener{
            val intent = Intent(this, AdminMinumanActivity::class.java)
            startActivity(intent)
        }
    }
}