package com.paybrother.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paybrother.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contacts.*


@Deprecated("Class is no more relevant, everything in ContactsPickerActivity with View Model")
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
