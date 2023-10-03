package com.paybrother.main.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReservationsInteractor @Inject constructor(
    private var reservationsRepository: ReservationsRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var _reservationsList = MutableLiveData<MutableList<Reservations>>()
    val reservationsList : LiveData<MutableList<Reservations>> get() = _reservationsList

    suspend fun fetchReservationsData(){
        coroutineScope.launch {
            _reservationsList.postValue(reservationsRepository.fetchReservations())
        }

    }

    fun insertNewReservation(reservation: ReservationItem){
        reservationsRepository.insertReservation(reservation)
    }

    fun deleteReservation(reservation: ReservationItem){
        reservationsRepository.deleteReservation(reservation.name)
    }

    fun updateReservation(state: ReservationUiState){
        reservationsRepository.updateReservation(
            state.id, state.name
        )
    }

}