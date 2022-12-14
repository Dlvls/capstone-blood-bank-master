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

class SignInActivity: AppCompatActivity() {

    private lateinit var binding:     ActivitySignInBinding
    private lateinit var auth:        FirebaseAuth
    private lateinit var mDatabase:   DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivitySignInBinding.inflate( layoutInflater )
        setContentView( binding.root )

        auth        = FirebaseAuth.getInstance()
        mDatabase   = FirebaseDatabase.getInstance().getReference( "User" )
        preferences = Preferences( this )

        setActionBtn()
    }

    private fun setActionBtn() {
        binding.btnLogin.setOnClickListener {
            val email    = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString()

            loginAccount( email, password )
        }

        binding.tvRegister.setOnClickListener {
            val intentRegister = Intent( this, SignUpActivity::class.java )

            startActivity( intentRegister )
        }

        binding.tvForgot.setOnClickListener {
            Toast.makeText( this, "Maaf, fitur ini belum tersedia.", Toast.LENGTH_SHORT ).show()
        }
    }

    private fun loginAccount( email: String, password: String ) {
        showLoading( true )

        auth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( this ) {
            if( it.isSuccessful ) {
                showLoading( false )

                Toast.makeText( this, "Masuk berhasil.", Toast.LENGTH_SHORT ).show()
                    val intentMain = Intent( this, MainActivity::class.java )

                    startActivity( intentMain )
                }
            else {
                showLoading( false )

                Toast.makeText( this, "${it.exception?.message}", Toast.LENGTH_LONG ).show()
            }
        }
    }

    private fun showLoading( loading: Boolean ) {
        if( loading ) {
            binding.showLoading.visibility = View.VISIBLE
        }
        else {
            binding.showLoading.visibility = View.GONE
        }
    }
}