package com.paybrother.main.app.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.paybrother.db.Reservations
import com.paybrother.db.MeetimeRoomDatabase
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationParcelable
import com.paybrother.main.app.repository.ReservationsRepository
import com.paybrother.main.app.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class LoanViewModel(application: Application) : ViewModel() {

    private var repository: ReservationsRepository? = null

    var allReservations : LiveData<List<Reservations>>? = null

    var reservation : Reservations? = null


    private val searchResults = MutableLiveData<List<Reservations>>()

    init {
        val roomDb = MeetimeRoomDatabase.getInstance(application)
        val reservationsDao = roomDb.reservationsDao()

        repository = ReservationsRepository(reservationsDao)


        allReservations = repository?.allReservations

        fetchData()
    }

//    fun fetchReservationData(){
//        reservation = savedStateHandle.get<Reservations>("reservationData") ?: error("reservationData")
//    }


    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            allReservations = repository?.allReservations
        }
    }

    // TODO refactor
    fun insertReservation(){
        repository?.insertReservation(ReservationItem(null, "Test+"+ Utils.random(), "TestEvent", "2010-05-30 22:15:52"))
        fetchData()
    }

    fun deleteReservation(name: String){
        repository?.deleteReservation(name)
        fetchData()
    }

}