package com.paybrother.main.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paybrother.db.Reservations
import com.paybrother.db.ReservationsDao
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

interface ReservationsRepository {
    fun insertReservation(reservation: ReservationItem)
    fun deleteReservation(name: String)
    fun updateReservation(state: ReservationUiState)
    suspend fun findReservation(name: String)
    fun fetchReservations() : MutableList<Reservations>

}

class ReservationsRepositoryImpl(
    private val reservationsDao: ReservationsDao
) : ReservationsRepository{

    val searchResults = MutableLiveData<List<Reservations>>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun insertReservation(reservation: ReservationItem) {
        reservationsDao.insertReservation(id = null, name = reservation.name, phoneNumber = reservation.phoneNumber, event = reservation.event, date = reservation.date)
    }

    override fun deleteReservation(name: String){
        reservationsDao.deleteReservation(name)
    }

    override fun updateReservation(state: ReservationUiState){
        coroutineScope.launch {
            reservationsDao.updateReservation(state.id, state.name)
        }
    }

    override suspend fun findReservation(name: String){
        searchResults.value = asyncFind(name).await()
    }

    override fun fetchReservations() : MutableList<Reservations> {
        return reservationsDao.getReservationList()
    }

    private fun asyncFind(name: String): Deferred<List<Reservations>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async reservationsDao.findReservation(name)
        }

}