package com.mfr.herbalplants.model

import com.google.firebase.database.Exclude

data class Minuman(
    var Nama:String? = null,
    var Jenis:String? = null,
    var ImageUrl:String? = null,
    var Description:String? = null,
    @get:Exclude
    @set:Exclude
    var key:String? = null
)