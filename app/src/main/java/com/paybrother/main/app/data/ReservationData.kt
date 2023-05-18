package com.paybrother.main.app.data

import java.util.*

data class ReservationData(val id: Long, val title: String, val sum: Int, val date: Date?)

data class ReservationParcelable(val id: Long, val title: String, val sum: Int, val date: Date?) : java.io.Serializable{

}
