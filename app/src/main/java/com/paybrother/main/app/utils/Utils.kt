package com.paybrother.main.app.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun convertStringToDate(date: String): Date? {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.parse(date)
    }


    fun convertStringToDate2(date: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.parse(date)
    }

    fun random(): String {
        val generator = Random()
        return (generator.nextInt(96) + 32).toString()
    }

}