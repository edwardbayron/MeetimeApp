package com.paybrother

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.paybrother.room.Reservation
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.register_procedure_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    @ApplicationContext val applicationContext: Context
) : ViewModel() {

    private var roomDb: ReservationDatabase? = null

    private val _proceduresList: MutableLiveData<ArrayList<ReservationItem>> = MutableLiveData()
    val proceduresList: LiveData<ArrayList<ReservationItem>> get() = _proceduresList

    val procedureChangedIndex = MutableLiveData<Int>()


    private val list = arrayListOf<ReservationItem>()

    init {
        Log.wtf("TAG: ", "wtf viewModel: ")
        roomDb = ReservationDatabase.getInstance(applicationContext)
        initProceduresList()
    }

    private fun initProceduresList() {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in roomDb?.reservationDao()?.getReservationList()!!) {
                list.add(ReservationItem(i.id!!, i.name, i.event, i.date))
            }

            withContext(Dispatchers.Main) {
                _proceduresList.value = list
            }
        }
    }

    fun insertProcedure(reservation: Reservation){
        CoroutineScope(Dispatchers.IO).launch {
            roomDb?.reservationDao()?.insertReservation(reservation)

            withContext(Dispatchers.Main) {
                list.add(ReservationItem(0, reservation.name, reservation.event, reservation.date))
                _proceduresList.value = list

            }
        }
    }

    fun deleteProcedure(id: Long){
        CoroutineScope(Dispatchers.IO).launch{
            val dbList = roomDb?.reservationDao()?.getReservationList()
            for(i in dbList!!){
                if(id == i.id){
                    roomDb?.reservationDao()?.deleteReservation(i)
                }
            }
            withContext(Dispatchers.Main){
                for(i in list){
                    if(id == i.id){
                        list.remove(i)
                    }
                }
                _proceduresList.value = list
            }
        }
    }

    fun editProcedure(reservation: Reservation){
        var index: Int = 0
        CoroutineScope(Dispatchers.IO).launch {
            val dbList = roomDb?.reservationDao()?.getReservationList()
            for(i in dbList!!){
                if(reservation.id == i.id){
                    roomDb?.reservationDao()?.updateReservation(reservation)
                }
            }

            withContext(Dispatchers.Main){

                for(i in list){
                    if(reservation.id == i.id){
                        index = list.indexOf(i)
                        list.set(index, ReservationItem(reservation.id!!, reservation.name, reservation.event, reservation.date))
                    }
                }
            }

            procedureChangedIndex.postValue(index)
            _proceduresList.postValue(list)
        }
    }


}