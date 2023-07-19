package com.paybrother.main.app.data

import java.util.*

data class ReservationData(val id: Long, val name: String, val phoneNumber: String, val event: String, val date: String?)

data class ReservationParcelable(val id: Long, val name: String, val phoneNumber: String, val event: String, val date: String?) : java.io.Serializable{

}
