package com.paybrother.main.app.viewmodels

import android.app.Application
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
import kotlinx.coroutines.launch

class LoanViewModel(application: Application) : ViewModel() {

    private var repository: ReservationsRepository? = null

    var allReservations : LiveData<List<Reservations>>? = null

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

    fun insertReservation(){
        repository?.insertReservation(ReservationItem(null, "Test+"+ Utils.random(), "TestEvent", "2010-05-30"))
        fetchData()
    }

    fun deleteReservation(name: String){
        repository?.deleteReservation(name)
        fetchData()
    }

    fun updateReservation(state: ReservationUiState){
        repository?.updateReservation(state)
        fetchData()
    }

}