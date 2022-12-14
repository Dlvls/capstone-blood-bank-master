package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_blood_bank.databinding.ActivityRecipientHistoryBinding
import com.example.capstone_blood_bank.ui.viewmodel.InputDataVIewModel

class RecipientHistoryActivity: AppCompatActivity() {

    private lateinit var binding:            ActivityRecipientHistoryBinding
    private lateinit var inputDataViewModel: InputDataVIewModel
    private lateinit var formName:           String
    private lateinit var formEmail:          String
    private lateinit var formBloodType:      String
    private lateinit var formDayEstimation:  String

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityRecipientHistoryBinding.inflate( layoutInflater )
        setContentView( binding.root )

        setToolbar()
        setInputData()

        binding.ivPreviousMenu.setOnClickListener {
            val intentPreviousMenu = Intent( this, MainActivity::class.java )

            startActivity( intentPreviousMenu )
        }
    }

    private fun setToolbar() {
        if( supportActionBar != null ) {
            supportActionBar?.setDisplayHomeAsUpEnabled( true )
            supportActionBar?.setDisplayShowTitleEnabled( false )
        }
    }

    private fun setInputData() {
        binding.btnRegister.setOnClickListener {
            formName          = binding.edtName.text.toString()
            formEmail         = binding.edtEmail.text.toString()
            formBloodType     = binding.edtBlood.text.toString()
            formDayEstimation = binding.edtDay.text.toString()

            if( formName.isEmpty() or formEmail.isEmpty() or ( formBloodType.isEmpty() ) or (formDayEstimation.isEmpty()) ) {
                Toast.makeText( this, "Tidak boleh ada data yang kosong!", Toast.LENGTH_SHORT ).show()
            }
            else {
                inputDataViewModel.insertData(
                    formName,
                    formEmail,
                    formBloodType,
                    formDayEstimation
                )

                Toast.makeText( this, "Pesanan berhasil.", Toast.LENGTH_SHORT ).show()
                finish()
            }
        }
    }

    override fun onOptionsItemSelected( item: MenuItem ): Boolean {
        if( item.itemId == android.R.id.home ) {
            finish()

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}