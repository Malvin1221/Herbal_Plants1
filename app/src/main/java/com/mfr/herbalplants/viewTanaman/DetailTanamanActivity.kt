package com.mfr.herbalplants.viewTanaman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfr.herbalplants.R
import com.mfr.herbalplants.utility.loadImage
import kotlinx.android.synthetic.main.activity_detail_tanaman.*

class DetailTanamanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tanaman)

        val intss =intent
        var namaT = intss.getStringExtra("NAMA")
        var jenisT = intss.getStringExtra("JENIS")
        var desT = intss.getStringExtra("DESKRIPSI")
        var imgT = intss.getStringExtra("IMGURI")

        nameDetailTextView.text = namaT
        jenisTanamaTextView.text = jenisT
        descriptionDetailTextView.text = desT
        tanamanDetailImageView.loadImage(imgT)


    }
}