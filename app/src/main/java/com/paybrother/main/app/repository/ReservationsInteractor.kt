package com.paybrother.main.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paybrother.db.Reservations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReservationsInteractor @Inject constructor(
    private var reservationsRepository: ReservationsRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var _reservationsList = MutableLiveData<List<Reservations>>()
    val reservationsList : LiveData<List<Reservations>> get() = _reservationsList

    suspend fun fetchReservationsData(){
        coroutineScope.launch {
            _reservationsList.postValue(reservationsRepository.fetchReservations())
        }

    }

}