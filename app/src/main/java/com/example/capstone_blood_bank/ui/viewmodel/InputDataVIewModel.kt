package com.example.capstone_blood_bank.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.capstone_blood_bank.database.BloodBankDao
import com.example.capstone_blood_bank.database.BloodBankResponses
import com.example.capstone_blood_bank.database.UserDatabase.Companion.getInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class InputDataVIewModel(app: Application) : AndroidViewModel(app) {

    var bloodBankDao: BloodBankDao? = null

    fun insertData(name: String, email: String, bloodType: String, dayEstimation: String) {
        Completable.fromAction {
            val modelDB = BloodBankResponses(0, "", "", "", "").apply {
                this.name = name
                this.email = email
                this.bloodType = bloodType
                this.dayEstimation = dayEstimation
                bloodBankDao?.insertData(this)
            }

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        bloodBankDao = getInstance(app)?.bloodBankDatabase?.bankSampahDao()
    }

}
