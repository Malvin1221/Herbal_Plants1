package com.mfr.herbalplants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfr.herbalplants.viewMinuman.AdminMinumanActivity
import com.mfr.herbalplants.viewTanaman.AdminTanamanActivity
import kotlinx.android.synthetic.main.activity_home_admin.*

class HomeAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        btnGOTOTanaman.setOnClickListener{
            startActivity(Intent(this@HomeAdminActivity,AdminTanamanActivity::class.java))
        }

        btnGOTOMinuman.setOnClickListener{
            startActivity(Intent(this@HomeAdminActivity,AdminMinumanActivity::class.java))
        }
    }
}