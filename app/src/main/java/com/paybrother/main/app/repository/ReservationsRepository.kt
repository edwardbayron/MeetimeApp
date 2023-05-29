package com.paybrother.main.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paybrother.db.Reservations
import com.paybrother.db.ReservationsDao
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import kotlinx.coroutines.*

class ReservationsRepository(private val reservationsDao: ReservationsDao) {

    val allReservations: LiveData<List<Reservations>> = reservationsDao.getReservationList()
    val searchResults = MutableLiveData<List<Reservations>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertReservation(reservation: ReservationItem){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.insertReservation(id = null, name = reservation.name, phoneNumber = reservation.phoneNumber, event = reservation.event, date = reservation.date)
        }
    }

    fun deleteReservation(name: String){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.deleteReservation(name)
        }
    }

    fun updateReservation(state: ReservationUiState){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.updateReservation(state.id, state.name)
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