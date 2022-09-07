package com.paybrother.contacts

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsPickerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {


    private var contactsUploadManager : ContactsUploadManager? = null

    private val originalContactsList = mutableListOf<ContactItem>()

    var contactsList = MutableLiveData<List<ContactItem>>()

    var list = ObservableField<List<ContactItem>>()

    init {

    }

    fun initContactsUploading(activity: FragmentActivity){
        contactsUploadManager = ContactsUploadManager(activity)
        viewModelScope.launch {
            for (i in contactsUploadManager?.retrieveList()!!) {
                originalContactsList.add(ContactItem(i.firstName, i.lastName))
            }
            contactsList.postValue(originalContactsList)
        }
    }
}