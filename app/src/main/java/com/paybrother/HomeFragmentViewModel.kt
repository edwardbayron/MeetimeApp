package com.paybrother

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {


    private var roomDb: ReservationDatabase? = null

    private val _proceduresList : MutableLiveData<List<ReservationItem>> = MutableLiveData()
    val proceduresList : LiveData<List<ReservationItem>> get() = _proceduresList

    init{
        roomDb = ReservationDatabase.getInstance(context)
    }



}