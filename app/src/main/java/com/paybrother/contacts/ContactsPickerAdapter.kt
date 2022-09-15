package com.paybrother.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paybrother.R

class ContactsPickerAdapter(var items: MutableList<ContactItem>, val callback: Callback) : RecyclerView.Adapter<ContactsPickerAdapter.ContactPickerViewHolder>() {

    private var selectedList = mutableListOf<ContactItem>()

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

            itemView.setOnClickListener{
                if(adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }

            checkbox.setOnClickListener {
                callback.onContactChecked(item)
            }
        }

        override fun onClick(view: View?) {

        }
    }

    interface Callback{
        fun onItemClicked(item: ContactItem)
        fun onContactChecked(item: ContactItem)
    }

    fun setData(data: MutableList<ContactItem>){
        items = data
        notifyDataSetChanged()
    }

}