package com.mfr.herbalplants.viewMinuman

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_admin_minuman2.*
import kotlinx.android.synthetic.main.activity_admin_tanaman.*

class AdminMinumanActivity : AppCompatActivity() {
    private  var MImageuri : Uri? = null
    private  var MStorageRef: StorageReference? = null
    private  var MDatabaseRef : DatabaseReference? = null
    private var MUploadTask : StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_minuman2)

        MStorageRef = FirebaseStorage.getInstance().getReference("minuman_upload")
        MDatabaseRef = FirebaseDatabase.getInstance("https://herbal-plants-cdda6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("minuman_upload")

        btn_cariMinuman.setOnClickListener{openFileChose()}
        btn_uploadMinuman.setOnClickListener{
            if (MUploadTask != null && MUploadTask!!.isInProgress){
                Toast.makeText(this@AdminMinumanActivity,
                    "Proses Upload Masih Berjalan", Toast.LENGTH_LONG).show()
            }else{
                uploadFile()
            }
        }
    }

    private fun uploadFile() {
        if (MImageuri != null) {
            val fileReference = MStorageRef!!.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(MImageuri!!)
            )
            progressBarMinuman.visibility = View.VISIBLE
            progressBarMinuman.isIndeterminate = true
            MUploadTask = fileReference.putFile(MImageuri!!)
                .addOnSuccessListener {
                    val handler = Handler()
                    handler.postDelayed(
                        {
                            progressBarMinuman.visibility = View.VISIBLE
                            progressBarMinuman.isIndeterminate = false
                            progressBarMinuman.progress = 0
                        }
                        ,500)
                    Toast.makeText(this@AdminMinumanActivity,
                        "Gambar Berhasil Di Upload",
                        Toast.LENGTH_LONG).show()
                    val upload = com.mfr.herbalplants.model.Minuman(
                        Nama = edt_namaMinuman.text.toString().trim{it <= ' '},
                        ImageUrl = MImageuri.toString(),
                        Jenis = edt_jenisMinuman.text.toString(),
                        Description = edt_deskMinuman.text.toString()
                    )
                    val uploadId = MDatabaseRef!!.push().key
                    MDatabaseRef!!.child(uploadId!!).setValue(upload)
                    progressBarMinuman.visibility = View.INVISIBLE
                    OpenImageActivity()
                }
                .addOnFailureListener{e->
                    progressBarMinuman.visibility = View.INVISIBLE
                    Toast.makeText(this@AdminMinumanActivity,
                        e.message,Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressBarMinuman.progress = progress.toInt()
                }
        }else{
            Toast.makeText(this@AdminMinumanActivity,
                "Gambar Belum Dipilih", Toast.LENGTH_LONG).show()
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST
            && resultCode == RESULT_OK
            && data != null
            && data.data != null
        ){
            MImageuri = data.data
            imgvToMinuman.setImageURI(MImageuri)
        }
    }
    private fun OpenImageActivity() {
        startActivity(Intent(this@AdminMinumanActivity, HomeActivity::class.java))
    }
    private fun openFileChose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
}
