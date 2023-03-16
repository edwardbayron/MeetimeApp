package com.paybrother.api

import com.paybrother.data.LoanData
import com.paybrother.utils.Utils.convertStringToDate
import java.util.*

object LoanApi {

    suspend fun getLoanData() : List<LoanData>{
        return listOf(
            LoanData(
                UUID.randomUUID().toString(), "Used Car: Subaru Impreza", 7000,
                convertStringToDate("18-07-2021")!!
            ),
            LoanData(
                UUID.randomUUID().toString(), "Gopro Hero 7", 500,
                convertStringToDate("31-01-2000")!!
            ),
            LoanData(
                UUID.randomUUID().toString(), "Sony Playstation 5", 798,
                convertStringToDate("01-12-2020")!!
            )
        )
    }

}