package com.paybrother.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.data.LoanData
import com.paybrother.api.LoanApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

}