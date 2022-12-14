package com.example.capstone_blood_bank.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var email:    String? = "",
    var name:     String? = "",
    var password: String? = "",
    var url:      String? = "",
): Parcelable