package com.mfr.herbalplants.model

import com.google.firebase.database.Exclude

data class Tanaman(
    var nama:String? = null,
    var jenis:String? = null,
    var imageUrl:String? = null,
    var description:String? = null,
    @get:Exclude
    @set:Exclude
    var key:String? = null
)
