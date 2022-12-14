package com.example.capstone_blood_bank.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_blood_bank.database.BloodBankResponses
import com.example.capstone_blood_bank.databinding.ActivityUserHistoryBinding
import com.example.capstone_blood_bank.ui.adapter.RiwayatAdapter
import com.example.capstone_blood_bank.ui.viewmodel.HistoryViewModel

class UserHistoryActivity: AppCompatActivity(), RiwayatAdapter.RiwayatAdapterCallback {

    private lateinit var binding:          ActivityUserHistoryBinding
    private lateinit var riwayatAdapter:   RiwayatAdapter
    private lateinit var riwayatViewModel: HistoryViewModel

    private var modelDatabaseList: MutableList<BloodBankResponses> = ArrayList()

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityUserHistoryBinding.inflate( layoutInflater )
        setContentView( binding.root )

        binding.imgPreviousMenu.setOnClickListener {
            val intentPreviousMenu = Intent( this, MainActivity::class.java )

            startActivity( intentPreviousMenu )
        }

        setLayout()
        setViewModel()
    }

    private fun setLayout() {
        binding.apply {
            tvNotFound.visibility   = View.GONE
            riwayatAdapter          = RiwayatAdapter( this@UserHistoryActivity, modelDatabaseList, this@UserHistoryActivity )
            rvHistory.setHasFixedSize( true )
            rvHistory.layoutManager = LinearLayoutManager( this@UserHistoryActivity )
            rvHistory.adapter       = riwayatAdapter
        }
    }

    private fun setViewModel() {
        riwayatViewModel = ViewModelProvider( this )[HistoryViewModel::class.java]

        riwayatViewModel.dataBank.observe( this ) { modelDatabases: List<BloodBankResponses> ->
            if( modelDatabases.isEmpty() ) {
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvHistory.visibility  = View.GONE
            }
            else {
                binding.rvHistory.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
            }

            riwayatAdapter.setUpData( modelDatabases )
        }
    }

    override fun onDelete( modelDatabase: BloodBankResponses? ) {
        val alertDialogBuilder = AlertDialog.Builder( this )
        val alertDialog        = alertDialogBuilder.create()

        alertDialogBuilder.setMessage( "Apakah yakin ingin menghapus riwayat ini?" )
        alertDialogBuilder.setPositiveButton( "Iya" ) { _: DialogInterface, _: Int ->
            val uid = modelDatabase?.id

            uid?.let {
                riwayatViewModel.deleteDataById(it)
            }

            Toast.makeText( this@UserHistoryActivity, "Riwayat berhasil dihapus.", Toast.LENGTH_SHORT ).show()
        }
        alertDialogBuilder.setNegativeButton( "Batal" ) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.cancel()
        }

        alertDialog.show()
    }

    override fun onOptionsItemSelected( item: MenuItem ): Boolean {
        if( item.itemId == android.R.id.home ) {
            finish()

            return true
        }

        return super.onOptionsItemSelected( item )
    }
}