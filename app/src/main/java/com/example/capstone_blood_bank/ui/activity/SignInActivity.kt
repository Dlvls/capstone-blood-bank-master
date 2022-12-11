package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.database.Users
import com.example.capstone_blood_bank.databinding.ActivitySignInBinding
import com.example.capstone_blood_bank.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    private lateinit var preferences: Preferences

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        auth = FirebaseAuth.getInstance()

        setActionBtn()
    }

    private fun setActionBtn() {
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            // pushLogin(email, password)
            loginAccount(email, password)
        }

        binding.tvLoginSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.tvLoginForgotPass.setOnClickListener {
            Toast.makeText(this, "Fitur Ini Belum Tersedia", Toast.LENGTH_SHORT).show()
        }

    }


    // Auth
    private fun loginAccount(email: String, password: String) {
        showLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showLoading(false)
                    Toast.makeText(this, "Login Account Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    showLoading(false)
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // RealtimeDatabase
    private fun pushLogin(email: String, password: String) {
        mDatabase.child(email).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.getValue(Users::class.java)
                if (users == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (users.password.equals(password)) {

                        preferences.setValues("nama", users.name.toString())
                        preferences.setValues("url", users.url.toString())
                        preferences.setValues("email", users.email.toString())
                        preferences.setValues("status", "1")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.showLoading.visibility = View.VISIBLE
        } else {
            binding.showLoading.visibility = View.GONE
        }
    }

}