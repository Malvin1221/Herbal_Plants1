package com.mfr.herbalplants.viewMinuman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.mfr.herbalplants.R
import com.mfr.herbalplants.adapter.ListAdapter
import com.mfr.herbalplants.adapter.ListAdapterMinuman
import com.mfr.herbalplants.model.Minuman
import com.mfr.herbalplants.model.Tanaman
import kotlinx.android.synthetic.main.activity_minuman2.*
import kotlinx.android.synthetic.main.activity_tanaman.*

class MinumanActivity : AppCompatActivity() {
    private var MStorage: FirebaseStorage? = null
    private var MDatabaseRef: DatabaseReference? = null
    private var MDBListener: ValueEventListener? = null
    private lateinit var MMinuman:MutableList<Minuman>
    private lateinit var listAdapterMinuman: ListAdapterMinuman

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minuman2)
        /**set adapter*/
        MRecyclerView.setHasFixedSize(true)
        MRecyclerView.layoutManager = LinearLayoutManager(this@MinumanActivity)
        MYDataLoaderProgressBar.visibility = View.VISIBLE
        MMinuman = ArrayList()
        listAdapterMinuman = ListAdapterMinuman(this@MinumanActivity,MMinuman)
        MRecyclerView.adapter = listAdapterMinuman
        /**set Firebase Database*/
        MStorage = FirebaseStorage.getInstance()
        MDatabaseRef = FirebaseDatabase.getInstance("https://herbal-plants-cdda6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("minuman_upload")
        MDBListener = MDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MinumanActivity,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                MMinuman.clear()
                for (minumanSnapshot in snapshot.children){
                    val Upload = minumanSnapshot.getValue(Minuman::class.java)
                    Upload!!.key = minumanSnapshot.key
                    MMinuman.add(Upload)
                }
                listAdapterMinuman.notifyDataSetChanged()
                MYDataLoaderProgressBar.visibility = View.GONE
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        MDatabaseRef!!.removeEventListener(MDBListener!!)
    }
}