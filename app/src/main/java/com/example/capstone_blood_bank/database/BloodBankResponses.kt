package com.example.capstone_blood_bank.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity( tableName = "waste_bank" )
@Parcelize
data class BloodBankResponses(
    @PrimaryKey( autoGenerate = true )
    var id: Int = 0,

    @ColumnInfo( name = "name" )
    var name: String,

    @ColumnInfo( name = "email" )
    var email: String,

    @ColumnInfo( name = "blood_type" )
    var bloodType: String,

    @ColumnInfo( name = "day_estimation" )
    var dayEstimation: String
): Parcelable