package com.paybrother.main.app.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoanViewModel(application) as T
    }
}

class ContactViewModelFactory(val application: Application) :
        ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T{
                return ContactSelectionViewModel(application) as T
            }
        }