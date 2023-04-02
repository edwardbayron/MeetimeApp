package com.paybrother.main.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paybrother.db.Reservations
import com.paybrother.db.ReservationsDao
import com.paybrother.main.app.data.ReservationItem
import kotlinx.coroutines.*
import java.lang.Math.random
import java.util.UUID

class ReservationsRepository(private val reservationsDao: ReservationsDao) {

    val allReservations: LiveData<List<Reservations>> = reservationsDao.getReservationList()
    val searchResults = MutableLiveData<List<Reservations>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertReservation(reservation: ReservationItem){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.insertReservation(Reservations(id = null, name = reservation.name, phoneNumber = reservation.name, event = reservation.event, date = reservation.date))
        }
    }

    fun deleteReservation(name: String){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.deleteReservation(name)
        }
    }

    fun findReservation(name: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Reservations>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async reservationsDao.findReservation(name)
        }

}