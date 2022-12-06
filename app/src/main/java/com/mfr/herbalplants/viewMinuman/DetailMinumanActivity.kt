package com.mfr.herbalplants.viewMinuman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfr.herbalplants.R
import com.mfr.herbalplants.utility.loadImage
import kotlinx.android.synthetic.main.activity_detail_minuman.*
import kotlinx.android.synthetic.main.activity_detail_tanaman.*

class DetailMinumanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_minuman)

        val intss =intent
        var namaT = intss.getStringExtra("NAMA")
        var jenisT = intss.getStringExtra("JENIS")
        var desT = intss.getStringExtra("DESKRIPSI")
        var imgT = intss.getStringExtra("IMGURI")

        minumanDetailTextView.text = namaT
        jenisMinumanTextView.text = jenisT
        descriptionDetailTextViewMinuman.text = desT
        minumanDetailImageView.loadImage(imgT)


    }
}