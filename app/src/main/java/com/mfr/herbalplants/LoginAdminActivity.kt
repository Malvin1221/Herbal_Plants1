package com.mfr.herbalplants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mfr.herbalplants.databinding.ActivityAdminTanamanBinding
import com.mfr.herbalplants.databinding.ActivityLoginAdminBinding
import com.mfr.herbalplants.viewTanaman.AdminTanamanActivity

class LoginAdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginAdminBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.adminBtn.setOnClickListener{
            val email = binding.emailAdmin.text.toString()
            val password = binding.passwordAdmin.text.toString()
            val intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)

            //Validasi Email
            if(email.isEmpty()){
                binding.emailAdmin.error = "Email Harus Diisi !!!"
                binding.passwordAdmin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Email Apakah Sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailAdmin.error = "Email Harus Diisi !!!"
                binding.passwordAdmin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Password
            if(password.isEmpty()){
                binding.emailAdmin.error = "Password Harus Diisi !!!"
                binding.passwordAdmin.requestFocus()
                return@setOnClickListener
            }
            LoginFireBaseAdmin(email, password)
        }
    }

    private fun LoginFireBaseAdmin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Login Berhasil$email", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,HomeAdminActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}