package com.paybrother.main.app.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.repository.ReservationsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val reservationsInteractor: ReservationsInteractor,
) : ViewModel() {

    private var _allReservations = MutableLiveData<List<Reservations>>(emptyList())
    val allReservations : LiveData<List<Reservations>> get() = _allReservations

    private var _uiState = MutableStateFlow(ReservationUiState(
        id = 0L,
        name = "",
        phoneNumber = "",
        event = "",
        date = "",
        time = "",
        notificationText = "",
        notificationTime = ""
        ))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reservationsInteractor.fetchReservationsData()
        }


        reservationsInteractor.reservationsList.observeForever(_allReservations::postValue)
    }

    fun selectedReservation(state: ReservationUiState){
        _uiState.update { state }
    }

    fun insertReservation(state: ReservationUiState){
        viewModelScope.launch(Dispatchers.IO){
            reservationsInteractor.insertNewReservation(
                ReservationItem(
                    state.name,
                    state.phoneNumber,
                    state.event,
                    state.date,
                    state.time,
                    state.notificationText,
                    state.notificationTime,
                    ))
            reservationsInteractor.fetchReservationsData()
        }
    }

    fun deleteReservation(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            reservationsInteractor.deleteReservation(name)
            reservationsInteractor.fetchReservationsData()
        }
    }

    fun updateReservation(state: ReservationUiState){
        viewModelScope.launch(Dispatchers.IO) {
            reservationsInteractor.updateReservation(state)
            reservationsInteractor.fetchReservationsData()
        }
    }

}