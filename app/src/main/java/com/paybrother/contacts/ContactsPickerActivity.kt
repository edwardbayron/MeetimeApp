package com.paybrother.contacts

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.paybrother.R
import com.paybrother.databinding.ActivityContactsPickerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsPickerActivity : FragmentActivity(){

    private var _binding : ActivityContactsPickerBinding? = null
    private val binding : ActivityContactsPickerBinding? get() = _binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_picker)
    }

}