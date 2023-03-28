package com.paybrother.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.data.LoanData
import com.paybrother.api.LoanApi
import com.paybrother.old.ReservationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class LoanViewModel : ViewModel() {

    val loanDataList = mutableStateListOf<LoanData>()

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch(Dispatchers.Main) {
            loanDataList.addAll(LoanApi.getLoanData())
        }
    }

    fun insertProcedure(reservation: LoanData){
        viewModelScope.launch(Dispatchers.IO) {
            //roomDb?.reservationDao()?.insertReservation(reservation)

            withContext(Dispatchers.Main) {
                loanDataList.add(LoanData(UUID.randomUUID().toString(), reservation.title, reservation.sum, reservation.date))
                //_proceduresList.value = list

            }
        }
    }

    fun deleteProcedure(id: String){
        viewModelScope.launch(Dispatchers.IO){
//            val dbList = roomDb?.reservationDao()?.getReservationList()
//            for(i in dbList!!){
//                if(id == i.id){
//                    roomDb?.reservationDao()?.deleteReservation(i)
//                }
//            }
            withContext(Dispatchers.Main){
                for(i in loanDataList){
                    if(id == i.id){
                        loanDataList.remove(i)
                    }
                }
                //_proceduresList.value = list
            }
        }
    }

    fun editProcedure(reservation: LoanData){
        var index: Int = 0
        viewModelScope.launch(Dispatchers.IO) {
            //val dbList = roomDb?.reservationDao()?.getReservationList()
//            for(i in loanDataList){
//                if(reservation.id == i.id){
//                    //roomDb?.reservationDao()?.updateReservation(reservation)
//                }
//            }

            withContext(Dispatchers.Main){

                for(i in loanDataList){
                    if(reservation.id == i.id){
                        index = loanDataList.indexOf(i)
                        loanDataList.set(index, LoanData(reservation.id, reservation.title, reservation.sum, reservation.date))
                    }
                }
            }

            //procedureChangedIndex.postValue(index)
            //_proceduresList.postValue(list)
        }
    }

}