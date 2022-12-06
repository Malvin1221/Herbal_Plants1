package com.mfr.herbalplants.viewTanaman

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.mfr.herbalplants.R
import com.mfr.herbalplants.adapter.ListAdapter
import com.mfr.herbalplants.model.Tanaman
import kotlinx.android.synthetic.main.activity_tanaman.*

class TanamanActivity : AppCompatActivity() {

    private var mStorage:FirebaseStorage? = null
    private var mDatabaseRef:DatabaseReference? = null
    private var mDBListener:ValueEventListener? = null
    private lateinit var mTanaman:MutableList<Tanaman>
    private lateinit var listAdapter:ListAdapter
//    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanaman)
//        searchView = findViewById(R.id.tVTanamanCari)
//        searchView?.clearFocus()

        /**set adapter*/
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this@TanamanActivity)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTanaman = ArrayList()
        
        listAdapter = ListAdapter(this@TanamanActivity,mTanaman)
        mRecyclerView.adapter = listAdapter
        
        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance("https://herbal-plants-cdda6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("tanaman_upload")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TanamanActivity,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                mTanaman.clear()
                for (tanamanSnapshot in snapshot.children){
                    val upload = tanamanSnapshot.getValue(Tanaman::class.java)
                    upload!!.key = tanamanSnapshot.key
                    mTanaman.add(upload)
                }
                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }
}
//
//private fun SearchView.setOnQueryTextListener(searchView: OnQueryTextListener) {
//
//}
