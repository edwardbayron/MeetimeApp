package com.paybrother

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ReservationsAdapter(var items: List<ReservationItem> = emptyList(), val callback: ReservationsAdapter.Callback) : RecyclerView.Adapter<ReservationsAdapter.ReservationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReservationsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_reservations, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ReservationsAdapter.ReservationsViewHolder, position: Int){
        holder.bind(items[position])
    }

    inner class ReservationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val name = itemView.findViewById<TextView>(R.id.reservations_firstName)
        private val event = itemView.findViewById<TextView>(R.id.reservations_event)
        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(item: ReservationItem){
            name.text = item.name
            event.text = item.event
            date.text = item.date

            itemView.setOnClickListener{
                if(adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback{
        fun onItemClicked(item: ReservationItem)
    }

    fun setData(data: List<ReservationItem>){
        items = data
        notifyDataSetChanged()
    }

}