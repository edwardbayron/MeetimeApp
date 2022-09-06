package com.paybrother.contacts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.paybrother.R

class ContactsPickerAdapter(var items: List<ContactItem>, val callback: Callback) : RecyclerView.Adapter<ContactsPickerAdapter.ContactPickerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactPickerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_contacts_picker, parent, false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ContactsPickerAdapter.ContactPickerViewHolder, position: Int){
        holder.bind(items[position])
    }

    inner class ContactPickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val contactName = itemView.findViewById<TextView>(R.id.contact_name_tv)
        private val phoneNumber = itemView.findViewById<TextView>(R.id.phone_number_tv)
        private val checkbox = itemView.findViewById<CheckBox>(R.id.contacts_picker_cb)

        fun bind(item: ContactItem){
            contactName.text = item.firstName
            phoneNumber.text = item.lastName

            //checkbox.text = items.get(position).toString()
            itemView.setOnClickListener{
                if(adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }

        override fun onClick(view: View?) {

        }
    }

    interface Callback{
        fun onItemClicked(item: ContactItem)
    }

}