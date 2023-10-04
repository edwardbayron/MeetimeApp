package com.paybrother.main.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.repository.ReservationsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val reservationsInteractor: ReservationsInteractor,
) : ViewModel() {

    private var _allReservations = MutableLiveData<List<Reservations>>(emptyList())
    val allReservations : LiveData<List<Reservations>> get() = _allReservations

    private var _uiState = MutableStateFlow(ReservationUiState(0L, "", "", "", ""))
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

    fun insertReservation(){
        viewModelScope.launch(Dispatchers.IO){
            reservationsInteractor.insertNewReservation(ReservationItem(_uiState.value.name, _uiState.value.phoneNumber, _uiState.value.event, _uiState.value.date))
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