package com.paybrother.main.app.viewmodels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.paybrother.db.Reservations
import com.paybrother.db.MeetimeRoomDatabase
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.repository.ReservationsRepository
import com.paybrother.main.app.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID

class LoanViewModel(application: Application) : ViewModel() {

    private var repository: ReservationsRepository? = null

    var allReservations : LiveData<List<Reservations>>? = null

    var uiState = MutableLiveData<ReservationUiState>()

    init {
        val roomDb = MeetimeRoomDatabase.getInstance(application)
        val reservationsDao = roomDb.reservationsDao()

        repository = ReservationsRepository(reservationsDao)

        allReservations = repository?.allReservations

        fetchData()
    }


    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            allReservations = repository?.allReservations
        }
    }

    fun selectedReservation(state: ReservationUiState){
        uiState.value = state
    }

    fun insertReservation(name: String, phoneNumber: String, event: String, date: String){
        repository?.insertReservation(ReservationItem(name, phoneNumber, event, date))
    }

    fun deleteReservation(name: String){
        repository?.deleteReservation(name)
    }

    fun updateReservation(state: ReservationUiState){
        repository?.updateReservation(state)
    }

}