package com.paybrother.main.app.viewmodels

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.main.app.compose.contacts.ContactsUploadHandler
import com.paybrother.main.app.data.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactSelectionViewModel @Inject constructor(application: Application) : ViewModel() {

    private var contactsUploadManager: ContactsUploadHandler? = null
    private val originalContactsList = mutableListOf<ContactItem>()
    var contactsList = MutableLiveData<MutableList<ContactItem>>()

    init {
//        initContactsUploading()
    }

    fun initContactsUploading(activity: ComponentActivity) {
        contactsUploadManager = ContactsUploadHandler(activity)
        viewModelScope.launch {
            for (i in contactsUploadManager?.retrieveList()!!) {
                originalContactsList.add(ContactItem(i.id, i.firstName, i.lastName))
            }
            contactsList.postValue(originalContactsList)
        }
    }
}