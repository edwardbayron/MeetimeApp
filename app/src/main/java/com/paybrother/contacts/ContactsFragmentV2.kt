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
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.paybrother.R
import com.paybrother.ReservationItem
import com.paybrother.ReservationsAdapter
import com.paybrother.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contacts_picker.*
import kotlinx.android.synthetic.main.fragment_contacts.*


@AndroidEntryPoint
class ContactsFragmentV2 : Fragment() {

    private var _binding : FragmentContactsBinding? = null
    private val binding : FragmentContactsBinding? get() = _binding

    private val viewModel: ContactsPickerViewModel by activityViewModels()
    lateinit var contactsPickerAdapter: ContactsPickerAdapter

    private var loadedContacts = mutableListOf<ContactItem>()

    val contactsPickerCallback = object : ContactsPickerAdapter.Callback{
        override fun onItemClicked(item: ContactItem) {

        }

        override fun onContactChecked(item: ContactItem) {
        }

    }


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
        requireActivity().title = "Contacts uploaded"

        contactsPickerAdapter = ContactsPickerAdapter(loadedContacts, contactsPickerCallback)
        contacts_upload_selected_rv?.adapter = contactsPickerAdapter
        contacts_upload_selected_rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.selectedContacts.observe(viewLifecycleOwner){
            contactsPickerAdapter.setData(it)
        }

    }




}
