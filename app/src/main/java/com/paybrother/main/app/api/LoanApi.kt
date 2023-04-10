package com.paybrother.main.app.api

import com.paybrother.main.app.data.ReservationData
import com.paybrother.main.app.utils.Utils.convertStringToDate
import java.util.*

object LoanApi {

    suspend fun getLoanData() : List<ReservationData>{
        return listOf(
            ReservationData(
                UUID.randomUUID().toString(), "Used Car: Subaru Impreza", 7000,
                convertStringToDate("18-07-2021")!!
            ),
            ReservationData(
                UUID.randomUUID().toString(), "Gopro Hero 7", 500,
                convertStringToDate("31-01-2000")!!
            ),
            ReservationData(
                UUID.randomUUID().toString(), "Sony Playstation 5", 798,
                convertStringToDate("01-12-2020")!!
            )
        )
    }

}