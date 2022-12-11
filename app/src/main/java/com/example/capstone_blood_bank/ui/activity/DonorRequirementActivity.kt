package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.R
import com.example.capstone_blood_bank.databinding.ActivityDonorRequirementBinding

class DonorRequirementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonorRequirementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorRequirementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()

        binding.imageView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun setToolBar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle(R.string.jenisSampah)
        }
    }
}