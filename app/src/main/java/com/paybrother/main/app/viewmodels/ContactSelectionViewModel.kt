package com.paybrother.main.app.viewmodels

import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.main.app.compose.contacts.ContactsUploadHandler
import com.paybrother.main.app.data.ContactItem
import kotlinx.coroutines.launch

class ContactSelectionViewModel : ViewModel() {

    private var contactsUploadManager: ContactsUploadHandler? = null
    private val originalContactsList = mutableListOf<ContactItem>()
    var contactsList = MutableLiveData<MutableList<ContactItem>>()

    init {

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