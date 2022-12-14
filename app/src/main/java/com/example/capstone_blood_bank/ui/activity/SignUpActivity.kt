package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.database.Users
import com.example.capstone_blood_bank.databinding.ActivitySignUpBinding
import com.example.capstone_blood_bank.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUpActivity: AppCompatActivity() {

    private lateinit var binding:           ActivitySignUpBinding
    private lateinit var auth:              FirebaseAuth
    private lateinit var mDatabase:         DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var preferences:       Preferences

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivitySignUpBinding.inflate( layoutInflater )
        setContentView( binding.root )

        auth              = FirebaseAuth.getInstance()
        mDatabase         = FirebaseDatabase.getInstance().reference
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference( "User" )
        preferences       = Preferences( this )

        setActionBtn()
    }

    private fun setActionBtn() {
        binding.btnRegister.setOnClickListener {
            val edtName  = binding.edtName.text.toString()
            val edtEmail = binding.edtEmail.text.toString()
            val edtPass  = binding.edtPassword.text.toString()

            regisAccount( edtEmail, edtPass )
            registerAccount( edtName, edtEmail, edtPass )
        }

        binding.tvLogin.setOnClickListener {
            val intentLogin = Intent( this, SignInActivity::class.java )

            startActivity( intentLogin )
        }
    }

    private fun regisAccount( edtEmail: String, edtPass: String ) {
        showLoading( true )

        auth.createUserWithEmailAndPassword( edtEmail, edtPass ).addOnCompleteListener(this) {
            if( it.isSuccessful ) {
                showLoading( false )
                emailVerification()

                Toast.makeText( this, "Daftar berhasil.", Toast.LENGTH_SHORT ).show()
            }
            else {
                showLoading( false )
                Toast.makeText( this, "${it.exception?.message}", Toast.LENGTH_LONG ).show()
            }
        }
    }

    private fun emailVerification() {
        val user = auth.currentUser

        user?.sendEmailVerification()?.addOnCompleteListener {
            if( it.isSuccessful ) {
                Toast.makeText( this, "Tautan verifikasi telah dikirimkan ke email Anda.", Toast.LENGTH_SHORT ).show()
            }
            else {
                Toast.makeText( this, "${it.exception?.message}", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    private fun registerAccount( edtName: String, edtEmail: String, edtPass: String ) {
        val users = Users()

        users.name     = edtName
        users.email    = edtEmail
        users.password = edtPass

        checkingUsername( edtName, users )
    }

    private fun checkingUsername( iName: String, data: Users ) {
        mFirebaseDatabase.child( iName ).addValueEventListener( object: ValueEventListener {
            override fun onDataChange( dataSnapshot: DataSnapshot ) {
                val users = dataSnapshot.getValue( Users::class.java )

                showLoading( true )

                if( users == null ) {
                    mFirebaseDatabase.child( iName ).setValue( data )

                    preferences.setValues( "nama", data.name.toString() )
                    preferences.setValues( "url", "" )
                    preferences.setValues( "email", data.email.toString() )
                    preferences.setValues( "status", "1" )

                    showLoading( false )
                }
            }

            override fun onCancelled( error: DatabaseError ) {
                Toast.makeText( this@SignUpActivity, error.message, Toast.LENGTH_SHORT ).show()
            }
        } )
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