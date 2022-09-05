package com.paybrother.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.paybrother.R
import com.paybrother.ReservationItem
import com.paybrother.ReservationsAdapter
import com.paybrother.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactsFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>,
    AdapterView.OnItemClickListener {

    @SuppressLint("InlinedApi")
    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.LOOKUP_KEY,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        else
            ContactsContract.Contacts.DISPLAY_NAME
    )

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private val SELECTION: String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"
        else
            "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?"

    // Defines a variable for the search string
    private val searchString: String = ""
    // Defines the array to hold values that replace the ?
    private val selectionArgs = arrayOf<String>(searchString)

    @SuppressLint("InlinedApi")
    private val FROM_COLUMNS: Array<String> = arrayOf(
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        } else {
            ContactsContract.Contacts.DISPLAY_NAME
        }
    )

    private val TO_IDS: IntArray = intArrayOf(R.id.firstName)

    private var _binding : FragmentContactsBinding? = null
    private val binding : FragmentContactsBinding? get() = _binding


    // Define variables for the contact the user selects
    // The contact's _ID value
    var contactId: Long = 0
    // The contact's LOOKUP_KEY
    var contactKey: String? = null
    // A content URI for the selected contact
    var contactUri: Uri? = null
    // An adapter that binds the result Cursor to the ListView
    private var cursorAdapter: SimpleCursorAdapter? = null
    private var cursorAdapterV2: SimpleCursorAdapter? = null

    private val contactsViewModel : ContactsFragmentViewModel by viewModels()

    private var onItemClickListener = object : ReservationsAdapter.Callback {
        override fun onItemClicked(item: ReservationItem) {

        }
    }

    var reservationsAdapter: ReservationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Contacts"


        cursorAdapter = SimpleCursorAdapter(
            requireActivity(),
            R.layout.listitem_contacts,
            null,
            FROM_COLUMNS, TO_IDS,
            0
        )
        // Sets the adapter for the ListView
        binding?.contactsRv?.adapter = cursorAdapter
        binding?.contactsRv?.onItemClickListener = this


        // Initializes the loader
        loaderManager.initLoader(0, null, this)

        val calContctPickerIntent =
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        calContctPickerIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(calContctPickerIntent, 1)


    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        val content_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        selectionArgs[0] = "%$searchString%"
        // Starts the query
        return activity?.let {
            return CursorLoader(
                it,
                content_uri,
                null,
                null,
                null,
                null
            )
        } ?: throw IllegalStateException()
    }

    var DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    var NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        // Put the result Cursor in the adapter for the ListView
        cursorAdapter?.swapCursor(data)

        val sb = StringBuilder()
        data?.moveToFirst()
        while(data?.isAfterLast == false){
            sb.append("\n" + data.getString(data.getColumnIndex(DISPLAY_NAME)!!))
            sb.append(":" + data.getString(data.getColumnIndex(NUMBER)!!))
            data?.moveToNext();
        }
        Log.wtf("TAG:","string builder: "+sb)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // Delete the reference to the existing Cursor
        cursorAdapter?.swapCursor(null)
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, adapterItemId: Long) {
        setupContactClickDialog()
    }

    private fun setupContactClickDialog(){
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialog)
        builder.setTitle("Chosen contact")

        var items = arrayOf("Open contact profile", "Create an event")
        builder.setItems(items
        ) { dialog, elementNumber ->
            when (elementNumber) {
                0 -> {}
                1 -> {}
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode === Activity.RESULT_OK) {
                val contctDataVar: Uri = data?.data!!
                val contctCursorVar: Cursor = requireContext().getContentResolver().query(
                    contctDataVar, null,
                    null, null, null
                )!!
                if (contctCursorVar.count > 0) {
                    while (contctCursorVar.moveToNext()) {
                        val ContctUidVar = contctCursorVar.getString(
                            contctCursorVar.getColumnIndex(ContactsContract.Contacts._ID)
                        )
                        val ContctNamVar = contctCursorVar.getString(
                            contctCursorVar.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )
                        Log.i("Names", ContctNamVar)
                        if (contctCursorVar.getString(
                                contctCursorVar.getColumnIndex(
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER
                                )
                            ).toInt() > 0
                        ) {
                            // Query phone here. Covered next
                            val ContctMobVar = contctCursorVar.getString(
                                contctCursorVar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            Log.i("Number", ContctMobVar)
                        }
                    }
                }
            }
        }
    }


}
