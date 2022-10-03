package com.paybrother.contacts

import android.content.Context
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybrother.room.Contact
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactsPickerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {


    private var contactsUploadManager: ContactsUploadManager? = null

    private val originalContactsList = mutableListOf<ContactItem>()

    var contactsList = MutableLiveData<MutableList<ContactItem>>()

    var selectedContacts = MutableLiveData<MutableList<ContactItem>>()

    var databaseContacts = MutableLiveData<MutableList<ContactItem>>()

    var list = ObservableField<List<ContactItem>>()
    private var roomDb: ReservationDatabase? = null

    init {
        roomDb = ReservationDatabase.getInstance(context)
        fetchDatabaseContacts()
    }

    fun initContactsUploading(activity: FragmentActivity) {
        contactsUploadManager = ContactsUploadManager(activity)
        viewModelScope.launch {
            for (i in contactsUploadManager?.retrieveList()!!) {
                originalContactsList.add(ContactItem(i.id, i.firstName, i.lastName))
            }
            contactsList.postValue(originalContactsList)
        }
    }

    fun sendSelectedContacts(list: MutableList<ContactItem>) {
        selectedContacts.postValue(list)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                list.map {
                    roomDb?.contactsDao()?.insertContact(Contact(null, it.firstName, it.lastName))
                }
            }
        }
    }

    private fun fetchDatabaseContacts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (roomDb?.contactsDao()?.getcontactList()?.isNotEmpty()!!) {
                    val ll = mutableListOf<ContactItem>()
                    for (i in roomDb?.contactsDao()?.getcontactList()!!) {
                        ll.add(ContactItem(i.id, i.displayName, i.phoneNumber))
                    }

                    databaseContacts.postValue(ll)
                }
            }
        }
    }

    fun deleteContact(id: Long){
        val ll = mutableListOf<ContactItem>()
        CoroutineScope(Dispatchers.IO).launch{
            val dbList = roomDb?.contactsDao()?.getcontactList()
            for(i in dbList!!){
                if(id == i.id){
                    roomDb?.contactsDao()?.deleteContact(i)
                }
            }
            for (i in roomDb?.contactsDao()?.getcontactList()!!) {
                ll.add(ContactItem(i.id, i.displayName, i.phoneNumber))
            }
            withContext(Dispatchers.Main){
//                for(i in list){
//                    if(id == i.id){
//                        list.remove(i)
//                    }
//                }


                databaseContacts.postValue(ll)
            }
        }
    }
}