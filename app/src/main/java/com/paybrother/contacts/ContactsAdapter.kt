package com.paybrother.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paybrother.R

class ContactsAdapter(var items: List<ContactItem>, val callback: Callback) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.listitem_contacts, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstName = itemView.findViewById<TextView>(R.id.firstName)
        private val lastName = itemView.findViewById<TextView>(R.id.lastName)

        fun bind(item: ContactItem) {
            firstName.text = item.firstName
            lastName.text = item.lastName
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: ContactItem)
    }

}