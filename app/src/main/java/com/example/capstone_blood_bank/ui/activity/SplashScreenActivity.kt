package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.utils.Preferences
import com.example.capstone_blood_bank.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SplashScreenActivity: AppCompatActivity() {

    private lateinit var binding:     ActivitySplashScreenBinding
    private lateinit var auth:        FirebaseAuth
    private lateinit var mDatabase:   DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivitySplashScreenBinding.inflate( layoutInflater )
        setContentView( binding.root )

        auth        = FirebaseAuth.getInstance()
        mDatabase   = FirebaseDatabase.getInstance().getReference( "User" )
        preferences = Preferences( this )
        preferences.setValues( "onboarding", "1" )

        Handler( Looper.getMainLooper() ).postDelayed( {
            if( preferences.getValues( "status" ).equals( "1" ) && auth.currentUser != null ) {
                val intentMain = Intent( this, MainActivity::class.java )

                finish()
                startActivity( intentMain )
            }
            else {
                val intentOnBoarding = Intent( this@SplashScreenActivity, OnBoardingActivity::class.java )

                startActivity( intentOnBoarding )
                finish()
            }
        }, 3000L )
    }
}