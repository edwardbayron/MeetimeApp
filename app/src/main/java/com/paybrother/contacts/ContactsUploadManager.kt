package com.paybrother.contacts

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.FragmentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.paybrother.R
import kotlinx.coroutines.*

class ContactsUploadManager(var activity: FragmentActivity) : LoaderManager.LoaderCallbacks<Cursor> {

    //private var cursorAdapter: SimpleCursorAdapter? = null
    private val TO_IDS: IntArray = intArrayOf(R.id.contact_name_tv)
    private var DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    private var NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    private var contactsList = mutableListOf<ContactItem>()

    init {
//        cursorAdapter = SimpleCursorAdapter(
//            activity,
//            R.layout.listitem_contacts_picker,
//            null,
//            null, TO_IDS,
//            0
//        )

        LoaderManager.getInstance(activity).initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val content_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // Starts the query
        return CursorLoader(
            activity,
            content_uri,
            null,
            null,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val sb = StringBuilder()
        var name = ""
        var phoneNumber = ""


        while(data?.moveToNext()!!){
            name = data.getString(data.getColumnIndex(DISPLAY_NAME))
            phoneNumber = data.getString(data.getColumnIndex(NUMBER))

            CoroutineScope(Dispatchers.Unconfined).launch {
                contactsList.add(ContactItem(null, name, phoneNumber))
            }

        }

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    suspend fun retrieveList() : List<ContactItem>{
        delay(3000)
        return contactsList
    }

}