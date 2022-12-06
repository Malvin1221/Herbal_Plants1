package com.mfr.herbalplants.utilityMinuman

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mfr.herbalplants.R


fun ImageView.loadImage(url : String?){
    val option = com.bumptech.glide.request.RequestOptions().placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}