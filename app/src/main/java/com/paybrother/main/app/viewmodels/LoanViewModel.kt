package com.paybrother.main.app.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.db.Reservations
import com.paybrother.db.MeetimeRoomDatabase
import com.paybrother.db.ReservationsDao
import com.paybrother.main.app.data.LoanData
import com.paybrother.main.app.api.LoanApi
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.repository.ReservationsRepository
import com.paybrother.main.app.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class LoanViewModel(application: Application) : ViewModel() {

    // TODO to refactor
    val loanDataList = mutableStateListOf<LoanData>()
    private var repository: ReservationsRepository? = null

    var allReservations : LiveData<List<Reservations>>? = null

    private val searchResults = MutableLiveData<List<Reservations>>()

    init {
        val roomDb = MeetimeRoomDatabase.getInstance(application)
        val reservationsDao = roomDb.reservationsDao()

        repository = ReservationsRepository(reservationsDao)


        allReservations = repository?.allReservations


        // TODO to refactor
        fetchData()

    }

    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            allReservations = repository?.allReservations
        }
    }

//    fun insertProcedure(reservation: LoanData){
//        viewModelScope.launch(Dispatchers.IO) {
//            //roomDb?.reservationDao()?.insertReservation(reservation)
//
//            withContext(Dispatchers.Main) {
//                loanDataList.add(LoanData(UUID.randomUUID().toString(), reservation.title, reservation.sum, reservation.date))
//                //_proceduresList.value = list
//
//            }
//        }
//    }

    fun insertReservation(){
        repository?.insertReservation(ReservationItem(null, "Test+"+ Utils.random(), "TestEvent", "date"))
        fetchData()
    }

    fun deleteReservation(name: String){
        repository?.deleteReservation(name)
        fetchData()
    }

}