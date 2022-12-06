package com.mfr.herbalplants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mfr.herbalplants.databinding.ActivityLoginBinding
import com.mfr.herbalplants.viewTanaman.AdminTanamanActivity


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

            auth = FirebaseAuth.getInstance()

        binding.tvToRegister.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)

            //Validasi Email
            if(email.isEmpty()){
                binding.edtEmailLogin.error = "Email Harus Diisi !!!"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Email Apakah Sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailLogin.error = "Email Harus Diisi !!!"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Password
            if(password.isEmpty()){
                binding.edtEmailLogin.error = "Password Harus Diisi !!!"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            LoginFireBase(email, password)
        }
    }

    private fun LoginFireBase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Login Berhasil$email", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}