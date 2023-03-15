//package com.paybrother.contacts
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.activity.viewModels
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.FragmentActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.paybrother.R
//import com.paybrother.databinding.ActivityContactsPickerBinding
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.activity_contacts_picker.*
//
//class ContactsPickerActivity : FragmentActivity(){
//
//
//    lateinit var contactsPickerAdapter: ContactsPickerAdapter
//    private val viewModel: ContactsPickerViewModel by viewModels()
//    lateinit var binding: ActivityContactsPickerBinding
//
//    private var loadedContacts = mutableListOf<ContactItem>()
//    private var checkboxTestList = mutableListOf<ContactItem>()
//
//    private val contactsPickerCallback = object : ContactsPickerAdapter.Callback{
//        override fun onItemClicked(item: ContactItem) {
//            item.id?.let {
//                viewModel.deleteContact(item.id)
//            }
//
//        }
//
//        override fun onContactChecked(item: ContactItem) {
//            checkboxTestList.add(item)
//        }
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_picker)
//
//        setupDataListeners()
//
//        contacts_picker_upload_btn?.setOnClickListener {
//            viewModel.initContactsUploading(this)
//        }
//
//        contacts_upload_accept?.setOnClickListener {
//            if(checkboxTestList.isNotEmpty()) {
//                viewModel.sendSelectedContacts(checkboxTestList)
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.contacts_picker_root_container, ContactsFragmentV2()).commit()
//            }
//            else{
//                Toast.makeText(this, "List is empty, nothing to upload", 1000).show()
//            }
//        }
//
//        contactsPickerAdapter = ContactsPickerAdapter(loadedContacts, contactsPickerCallback)
//        contacts_picker_rv?.adapter = contactsPickerAdapter
//        contacts_picker_rv.layoutManager = LinearLayoutManager(this)
//
//
//
//    }
//
//    private fun setupDataListeners(){
//        viewModel.contactsList.observe(this) {
//            contactsPickerAdapter.setData(it)
//        }
//
//        viewModel.databaseContacts.observe(this){
//            if(it.isNotEmpty()) {
//                loadedContacts = it
//                contactsPickerAdapter.setData(it)
//                contacts_picker_upload_btn.visibility = View.GONE
//                contacts_upload_accept.visibility = View.GONE
//            }
//            else{
//                contacts_picker_upload_btn.visibility = View.VISIBLE
//                contacts_upload_accept.visibility = View.VISIBLE
//
//            }
//        }
//
//    }
//
//}