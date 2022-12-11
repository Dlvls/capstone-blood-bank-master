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

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    private lateinit var preferences: Preferences

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        auth = FirebaseAuth.getInstance()

        setActionBtn()

    }

    private fun setActionBtn() {
        binding.btnRegister.setOnClickListener {
            val edtName = binding.fullNameEditText.text.toString()
            val edtEmail = binding.emailEditText.text.toString()
            val edtPass = binding.passwordEditText.text.toString()

            regisAccount(edtEmail, edtPass)
            registerAccount(edtName, edtEmail, edtPass)
        }

        binding.tvRegisterSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    // Auth
    private fun regisAccount(edtEmail: String, edtPass: String) {
        showLoading(true)
        auth.createUserWithEmailAndPassword(edtEmail, edtPass)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showLoading(false)
                    emailVerification()
                    Toast.makeText(
                        this,
                        "Register Berhasil, Silahkan Verifikasi Email",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    showLoading(false)
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun emailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Email Verifikasi Telah Dikirim", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // RealtimeDatabase
    private fun registerAccount(edtName: String, edtEmail: String, edtPass: String) {
        val users = Users()
        users.name = edtName
        users.email = edtEmail
        users.password = edtPass

        checkingUsername(edtName, users)
    }

    private fun checkingUsername(iName: String, data: Users) {
        mFirebaseDatabase.child(iName).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.getValue(Users::class.java)
                showLoading(true)
                if (users == null) {
                    mFirebaseDatabase.child(iName).setValue(data)

                    preferences.setValues("nama", data.name.toString())
                    preferences.setValues("url", "")
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("status", "1")

                    showLoading(false)
//                    val intent = Intent(
//                        this@RegisterActivity,
//                        RegisterPhotoActivity::class.java
//                    ).putExtra("data", data.name)
//                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, error.message, Toast.LENGTH_SHORT).show()
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