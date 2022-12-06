package com.mfr.herbalplants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfr.herbalplants.viewMinuman.MinumanActivity
import com.mfr.herbalplants.viewTanaman.TanamanActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imgvToTanaman.setOnClickListener{
            startActivity(Intent(this@HomeActivity,TanamanActivity::class.java))
        }
        imgvToMinuman.setOnClickListener {
            startActivity(Intent(this@HomeActivity,MinumanActivity::class.java))
        }
        txtDeveloper.setOnClickListener{
            startActivity(Intent(this@HomeActivity,LoginAdminActivity::class.java))
        }
    }
}