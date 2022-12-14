package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        binding.cvForm.setOnClickListener {
            val intentForm = Intent( this, RecipientHistoryActivity::class.java )

            startActivity( intentForm )
        }

        binding.cvDonor.setOnClickListener {
            val intentDonor = Intent( this, UserHistoryActivity::class.java )

            startActivity( intentDonor )
        }

        binding.cvReq.setOnClickListener {
            val intentRequirement = Intent( this, DonorRequirementActivity::class.java )

            startActivity( intentRequirement )
        }
    }
}