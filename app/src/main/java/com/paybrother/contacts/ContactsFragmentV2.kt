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
class ContactsFragmentV2 : Fragment(), LoaderManager.LoaderCallbacks<Cursor>,
    AdapterView.OnItemClickListener {

    private var _binding : FragmentContactsBinding? = null
    private val binding : FragmentContactsBinding? get() = _binding

    private var cursorAdapter: SimpleCursorAdapter? = null

    private val contactsViewModel : ContactsFragmentViewModel by viewModels()

    private val TO_IDS: IntArray = intArrayOf(R.id.firstName)
    var DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    var NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER


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
            null, TO_IDS,
            0
        )
        // Sets the adapter for the ListView
        binding?.contactsRv?.adapter = cursorAdapter
        binding?.contactsRv?.onItemClickListener = this

        // Initializes the loader
        loaderManager.initLoader(0, null, this)

//        val calContctPickerIntent =
//            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
//        calContctPickerIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
//        startActivityForResult(calContctPickerIntent, 1)
//

    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        val content_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
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



    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        val sb = StringBuilder()
        data?.moveToFirst()
        while(data?.isAfterLast == false){
            sb.append("\n" + data.getString(data.getColumnIndex(DISPLAY_NAME)!!))
            sb.append(":" + data.getString(data.getColumnIndex(NUMBER)!!))
            data?.moveToNext();
        }
        Log.wtf("TAG:","string builder: "+sb)
        // Put the result Cursor in the adapter for the ListView
        cursorAdapter?.swapCursor(data) // TODO WILL BRING NULLPOINTER, LSIT IS NULL, BUT CHECK LOGCAT
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


}
