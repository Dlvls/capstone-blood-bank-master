package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Donor
        binding.donor.setOnClickListener {
            startActivity(Intent(this, RecipientHistoryActivity::class.java))
        }

        // Riwayat
        binding.cardView8.setOnClickListener {
            startActivity(Intent(this, UserHistoryActivity::class.java))
        }

        // Requirement
        binding.requirement.setOnClickListener {
            startActivity(Intent(this, DonorRequirementActivity::class.java))
        }

    }
}