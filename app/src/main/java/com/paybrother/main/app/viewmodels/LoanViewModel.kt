package com.paybrother.main.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.repository.ReservationsInteractor
import com.paybrother.main.app.repository.ReservationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val reservationsRepository: ReservationsRepository,
    private val reservationsInteractor: ReservationsInteractor,
) : ViewModel() {

    private var _allReservations = MutableLiveData<List<Reservations>>()
    val allReservations : LiveData<List<Reservations>> get() = _allReservations

    private var _uiState = MutableLiveData<ReservationUiState>()
    val uiState: LiveData<ReservationUiState> get() = _uiState

    init {
        viewModelScope.launch {
            reservationsInteractor.fetchReservationsData()
        }

        reservationsInteractor.reservationsList.observeForever(_allReservations::postValue)
    }

    fun selectedReservation(state: ReservationUiState){
        _uiState.postValue(state)
    }

    fun insertReservation(uiState: ReservationUiState){
        viewModelScope.launch{
            reservationsRepository.insertReservation(ReservationItem(uiState.name, uiState.phoneNumber, uiState.event, uiState.date))
        }
    }

    fun deleteReservation(uiState: ReservationUiState){
        viewModelScope.launch {
            reservationsRepository.deleteReservation(uiState.name)
        }
    }

    fun updateReservation(state: ReservationUiState){
        viewModelScope.launch {
            reservationsRepository.updateReservation(state)
        }
    }

}