package com.paybrother.main.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.repository.ReservationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val reservationsRepository: ReservationsRepository,
) : ViewModel() {

    private var _allReservations : MutableLiveData<List<Reservations>> = MutableLiveData(listOf())
    val allReservations : LiveData<List<Reservations>> get() = _allReservations

    private var _uiState : MutableLiveData<ReservationUiState> = MutableLiveData<ReservationUiState>()
    val uiState: LiveData<ReservationUiState> get() = _uiState

    init {
        fetchData()
    }


    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            _allReservations.postValue(reservationsRepository.fetchReservations())
        }
    }

    fun selectedReservation(state: ReservationUiState){
        _uiState.postValue(state)
    }

    fun insertReservation(uiState: ReservationUiState?){
        uiState?.let {
            reservationsRepository.insertReservation(ReservationItem(it.name, it.phoneNumber, it.event, it.date))
        }
    }

    fun deleteReservation(uiState: ReservationUiState?){
        uiState?.let {
            reservationsRepository.deleteReservation(it.name)
        }
    }

    fun updateReservation(state: ReservationUiState){
        reservationsRepository.updateReservation(state)
    }

}