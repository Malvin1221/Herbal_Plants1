package com.mfr.herbalplants.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfr.herbalplants.R
import com.mfr.herbalplants.model.Minuman
import com.mfr.herbalplants.utility.loadImage
import com.mfr.herbalplants.viewMinuman.DetailMinumanActivity
import com.mfr.herbalplants.viewTanaman.DetailTanamanActivity

class ListAdapterMinuman(var Mcontex: Context, var minumanList:List<Minuman>):
    RecyclerView.Adapter<ListAdapterMinuman.ListViewHolder>()
{

    inner class ListViewHolder (var v : View): RecyclerView.ViewHolder(v){
        var imgM = v.findViewById<ImageView>(R.id.minumanImageView)
        var namaM = v.findViewById<TextView>(R.id.nameTextViewMinuman)
        var deskripM = v.findViewById<TextView>(R.id.descriptionTextViewMinuman)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.row_minuman,parent,false)
        return ListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var newList = minumanList[position]
        holder.namaM.text = newList.Nama
        holder.deskripM.text = newList.Description
        holder.imgM.loadImage(newList.ImageUrl)
        holder.v.setOnClickListener{

            val nama = newList.Nama
            val jenis = newList.Jenis
            val deskrip = newList.Description
            val imgUri = newList.ImageUrl

            val Mintent = Intent(Mcontex, DetailMinumanActivity::class.java)
            Mintent.putExtra("NAMA",nama)
            Mintent.putExtra("JENIS",jenis)
            Mintent.putExtra("DESKRIPSI",deskrip)
            Mintent.putExtra("IMGURI",imgUri)
            Mcontex.startActivity(Mintent)
        }
    }

    override fun getItemCount(): Int = minumanList.size

}