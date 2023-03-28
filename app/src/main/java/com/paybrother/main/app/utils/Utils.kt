package com.paybrother.main.app.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun convertStringToDate(date: String): Date? {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.parse(date)
    }

}