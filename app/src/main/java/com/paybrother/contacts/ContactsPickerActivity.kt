package com.paybrother.contacts

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.FragmentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.paybrother.R
import com.paybrother.room.Contact
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contacts_picker.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ContactsPickerActivity : FragmentActivity(), LoaderManager.LoaderCallbacks<Cursor> {


    private var cursorAdapter: SimpleCursorAdapter? = null
    lateinit var contactsPickerAdapter: ContactsPickerAdapter


    private val TO_IDS: IntArray = intArrayOf(R.id.contact_name_tv)
    var DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    var NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER

    val items  = listOf(ContactItem("Irina", "55554444"), ContactItem("Tanja", "33335555"))
    val loadedContacts = mutableListOf<ContactItem>()

    val contactsPickerCallback = object : ContactsPickerAdapter.Callback{
        override fun onItemClicked(item: ContactItem) {

        }

    }

    private var roomDb : ReservationDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_picker)
        roomDb = ReservationDatabase.getInstance(this)

        contactsPickerAdapter = ContactsPickerAdapter(loadedContacts, contactsPickerCallback)

        cursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.listitem_contacts_picker,
            null,
            null, TO_IDS,
            0
        )

        LoaderManager.getInstance(this).initLoader(0, null, this)

        contacts_picker_rv?.adapter = contactsPickerAdapter
        contacts_picker_rv.layoutManager = LinearLayoutManager(this);


        CoroutineScope(Dispatchers.IO).launch {

            val list = roomDb?.contactsDao()?.getcontactList()

            for(i in list!!){
                loadedContacts.add(ContactItem(i.displayName, i.phoneNumber))
            }

            for(i in loadedContacts){
                Log.wtf("CONTACT IO: ", "i: "+i.firstName)
                Log.wtf("CONTACT IO : ", "i: "+i.lastName)
            }
        }




    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        val content_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // Starts the query
            return CursorLoader(
                this,
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
        data?.moveToFirst()
        while(data?.isAfterLast == false){
            //sb.append("\n" + data.getString(data.getColumnIndex(DISPLAY_NAME)))
            //sb.append(":" + data.getString(data.getColumnIndex(NUMBER)))
            name = data.getString(data.getColumnIndex(DISPLAY_NAME))
            phoneNumber = data.getString(data.getColumnIndex(NUMBER))
            //loadedContacts.plus(ContactItem(name, phoneNumber))
            CoroutineScope(Dispatchers.IO).launch {
                roomDb?.contactsDao()?.insertContact(Contact(null, name, phoneNumber))
            }
            data?.moveToNext();

        }
        //Log.wtf("TAG:","string builder: "+sb)

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }


}