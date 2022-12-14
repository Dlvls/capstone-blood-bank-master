package com.example.capstone_blood_bank.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [BloodBankResponses::class], version = 1, exportSchema = false )
abstract class BloodBankDatabase: RoomDatabase() {

    abstract fun bankSampahDao(): BloodBankDao
}