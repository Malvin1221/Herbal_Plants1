package com.mfr.herbalplants.viewTanaman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.mfr.herbalplants.HomeActivity
import com.mfr.herbalplants.R
import kotlinx.android.synthetic.main.activity_admin_tanaman.*

class AdminTanamanActivity : AppCompatActivity() {

    private  var mImageuri : Uri? = null
    private  var mStorageRef: StorageReference? = null
    private  var mDatabaseRef : DatabaseReference? = null
    private var mUploadTask : StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tanaman)

        mStorageRef = FirebaseStorage.getInstance().getReference("tanaman_upload")
        mDatabaseRef = FirebaseDatabase.getInstance("https://herbal-plants-cdda6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("tanaman_upload")

        btn_cariTanaman.setOnClickListener{openFileChose()}
        btn_uploadTanaman.setOnClickListener{
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@AdminTanamanActivity,
                    "Proses Upload Masih Berjalan", Toast.LENGTH_LONG).show()
            }else{
                uploadFile()
            }
        }
    }

    private fun uploadFile() {
        if (mImageuri != null) {
            val fileReference = mStorageRef!!.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(mImageuri!!)
            )
            progressBarTanaman.visibility = View.VISIBLE
            progressBarTanaman.isIndeterminate = true
            mUploadTask = fileReference.putFile(mImageuri!!)
                .addOnSuccessListener {
                    val handler = Handler()
                    handler.postDelayed(
                        {
                            progressBarTanaman.visibility = View.VISIBLE
                            progressBarTanaman.isIndeterminate = false
                            progressBarTanaman.progress = 0
                        }
                        ,500)
                    Toast.makeText(this@AdminTanamanActivity,
                        "Gambar Berhasil Di Upload",
                        Toast.LENGTH_LONG).show()
                    val upload = com.mfr.herbalplants.model.Tanaman(
                        nama = edt_namaTanaman.text.toString().trim{it <= ' '},
                        imageUrl = mImageuri.toString(),
                        jenis = edt_jenisTanaman.text.toString(),
                        description= edt_deskTanaman.text.toString()
                    )
                    val uploadId = mDatabaseRef!!.push().key
                    mDatabaseRef!!.child(uploadId!!).setValue(upload)
                    progressBarTanaman.visibility = View.INVISIBLE
                    OpenImageActivity()
                }
                .addOnFailureListener{e->
                    progressBarTanaman.visibility = View.INVISIBLE
                    Toast.makeText(this@AdminTanamanActivity,
                    e.message,Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressBarTanaman.progress = progress.toInt()
                }
        }else{
            Toast.makeText(this@AdminTanamanActivity,
            "Gambar Belum Dipilih", Toast.LENGTH_LONG).show()
        }
    }


    private fun OpenImageActivity() {
        startActivity(Intent(this@AdminTanamanActivity,HomeActivity::class.java))
    }

    private fun openFileChose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST
            && resultCode == RESULT_OK
            && data != null
            && data.data != null
        ){
            mImageuri = data.data
            imgvTanaman.setImageURI(mImageuri)
        }
    }
    private fun getFileExtension(uri: Uri):String?{
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
}