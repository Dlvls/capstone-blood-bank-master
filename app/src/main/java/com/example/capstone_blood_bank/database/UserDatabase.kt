package com.example.capstone_blood_bank.database

import android.content.Context
import androidx.room.Room

class UserDatabase  private constructor( context: Context ) {

        var bloodBankDatabase: BloodBankDatabase

        companion object {
            private var mInstance: UserDatabase? = null

            fun getInstance( context: Context ): UserDatabase? {
                if( mInstance == null ) {
                    mInstance = UserDatabase( context )
                }
                return mInstance
            }
        }

        init {
            bloodBankDatabase = Room.databaseBuilder( context, BloodBankDatabase:: class . java , "wastebank_db" )
                .fallbackToDestructiveMigration ()
                .build ()
        }
}