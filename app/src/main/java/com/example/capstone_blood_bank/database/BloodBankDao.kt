package com.example.capstone_blood_bank.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BloodBankDao {

    @Query( "SELECT * FROM waste_bank" )
    fun getAll(): LiveData<List<BloodBankResponses>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    fun insertData( modelDatabases: BloodBankResponses )

    @Query( "DELETE FROM waste_bank WHERE id= :id" )
    fun deleteSingleData( id: Int )
}