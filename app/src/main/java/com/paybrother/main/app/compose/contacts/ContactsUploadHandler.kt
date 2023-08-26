package com.paybrother.main.app.compose.contacts

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.paybrother.main.app.data.ContactItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsUploadHandler(var activity: ComponentActivity) : LoaderManager.LoaderCallbacks<Cursor> {

    private var DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    private var NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    private var contactsList = mutableListOf<ContactItem>()

    init {
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