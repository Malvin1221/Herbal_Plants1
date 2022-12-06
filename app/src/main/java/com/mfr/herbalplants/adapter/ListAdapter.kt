package com.mfr.herbalplants.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.common.io.CharStreams
import com.mfr.herbalplants.R
import com.mfr.herbalplants.model.Tanaman
import com.mfr.herbalplants.utility.loadImage
import com.mfr.herbalplants.viewTanaman.DetailTanamanActivity
import com.mfr.herbalplants.viewTanaman.TanamanActivity

class ListAdapter(var mContex:Context, var tanamanList:List<Tanaman>):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v :View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.tanamanImageView)
        var namaT = v.findViewById<TextView>(R.id.nameTextView)
        var deskripT = v.findViewById<TextView>(R.id.descriptionTextView)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.row_tanaman,parent,false)
        return ListViewHolder(v)
    }
    override fun getItemCount(): Int = tanamanList.size

//    override fun getFilter(): Filter{
//        return object : Filter(){
//            override fun performFiltering(constraint:CharSequence?) : FilterResults{
//                val charString = constraint.toString()
//                if (charString.isEmpty()){
//                    filterTanaman = Tanaman
//                }else{
//                  val filteredList = ArrayList<TanamanActivity>()
//                    Tanaman
//                        .filter{
//                            (it.)}
//                }
//            }


//            override fun publishResults(p0: CharSequence?,results: FilterResults) {
//             filterTanaman = if(result?.values == null)
//                 ArrayList()
//                else
//                    results.value as ArrayList<TanamanActivity>
//                notifyDataSetChanged()
//            }
override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    var newList = tanamanList[position]
    holder.namaT.text = newList.nama
    holder.deskripT.text = newList.description
    holder.imgT.loadImage(newList.imageUrl)
    holder.v.setOnClickListener{

        val nama = newList.nama
        val jenis = newList.jenis
        val deskrip = newList.description
        val imgUri = newList.imageUrl

        val mIntent = Intent(mContex, DetailTanamanActivity::class.java)
        mIntent.putExtra("NAMA",nama)
        mIntent.putExtra("JENIS",jenis)
        mIntent.putExtra("DESKRIPSI",deskrip)
        mIntent.putExtra("IMGURI",imgUri)
        mContex.startActivity(mIntent)
    }
}
}