package com.example.capstone_blood_bank.utils

import java.text.DecimalFormat

object FunctionHelper {

    fun rupiahFormat( price: Int ): String {
        val formatter = DecimalFormat( "#,###" )

        return "Rp " + formatter.format( price.toLong() )
    }
}