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

class UserHistoryActivity : AppCompatActivity(), RiwayatAdapter.RiwayatAdapterCallback {

    private lateinit var binding: ActivityUserHistoryBinding
    private var modelDatabaseList: MutableList<BloodBankResponses> = ArrayList()
    private lateinit var riwayatAdapter: RiwayatAdapter
    private lateinit var riwayatViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        setLayout()
        setViewModel()
    }

    private fun setLayout() {
        binding.apply {
            tvNotFound.visibility = View.GONE
            riwayatAdapter =
                RiwayatAdapter(
                    this@UserHistoryActivity,
                    modelDatabaseList,
                    this@UserHistoryActivity
                )
            rvHistory.setHasFixedSize(true)
            rvHistory.layoutManager = LinearLayoutManager(this@UserHistoryActivity)
            rvHistory.adapter = riwayatAdapter
        }
    }

    private fun setViewModel() {
        riwayatViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        riwayatViewModel.dataBank.observe(this) { modelDatabases: List<BloodBankResponses> ->
            if (modelDatabases.isEmpty()) {
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.rvHistory.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
            }
            riwayatAdapter.setUpData(modelDatabases)
        }
    }

    override fun onDelete(modelDatabase: BloodBankResponses?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Hapus riwayat ini?")
        alertDialogBuilder.setPositiveButton("Ya, Hapus") { _: DialogInterface, _: Int ->
            val uid = modelDatabase?.id
            uid?.let { riwayatViewModel.deleteDataById(it) }
            Toast.makeText(
                this@UserHistoryActivity,
                "Data yang dipilih sudah dihapus",
                Toast.LENGTH_SHORT
            ).show()
        }

        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, _: Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}