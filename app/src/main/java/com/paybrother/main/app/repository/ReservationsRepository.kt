package com.paybrother.main.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paybrother.db.Reservations
import com.paybrother.db.ReservationsDao
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import kotlinx.coroutines.*

interface ReservationsRepository {
    fun insertReservation(reservation: ReservationItem)
    fun deleteReservation(name: String)
    fun updateReservation(state: ReservationUiState)
    fun findReservation(name: String)
    fun fetchReservations(): List<Reservations>

}

class ReservationsRepositoryImpl(
    private val reservationsDao: ReservationsDao
) : ReservationsRepository{

    val searchResults = MutableLiveData<List<Reservations>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun insertReservation(reservation: ReservationItem) {
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.insertReservation(id = null, name = reservation.name, phoneNumber = reservation.phoneNumber, event = reservation.event, date = reservation.date)
        }
    }

    override fun deleteReservation(name: String){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.deleteReservation(name)
        }
    }

    override fun updateReservation(state: ReservationUiState){
        coroutineScope.launch(Dispatchers.IO) {
            reservationsDao.updateReservation(state.id, state.name)
        }
    }

    override fun findReservation(name: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    override fun fetchReservations(): List<Reservations> {
        return reservationsDao.getReservationList()
    }

    private fun asyncFind(name: String): Deferred<List<Reservations>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async reservationsDao.findReservation(name)
        }

}