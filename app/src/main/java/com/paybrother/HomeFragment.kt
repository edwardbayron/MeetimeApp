package com.paybrother

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paybrother.room.Reservation
import com.paybrother.room.database.ReservationDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_reservations.*

class HomeFragment : Fragment() {

    val reservationItems = arrayListOf<ReservationItem>()

    var reservationsAdapter : ReservationsAdapter? = null
    private var dialog : BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var reservationDb : ReservationDatabase = ReservationDatabase.getInstance(requireContext())

        setupDatabaseList(reservationDb)


        reservationsAdapter = ReservationsAdapter(reservationItems, object : ReservationsAdapter.Callback {
            override fun onItemClicked(item: ReservationItem) {

                setupProcedureDetailsDialog(item, reservationDb)

            }
        })

        reservations_rv.adapter = reservationsAdapter

    }

    private fun setupDatabaseList(reservationDb: ReservationDatabase){
        Thread {
            for(i in reservationDb.reservationDao().getReservationList()){
                reservationItems.add(ReservationItem(i.id!!,  i.name, i.event, i.date))
            }
        }.start()
    }

    private fun setupProcedureDetailsDialog(item: ReservationItem, reservationDb: ReservationDatabase){
        val dialogView = requireActivity().layoutInflater.inflate(R.layout.bottom_sheet_procedure_details, null)
        dialog = BottomSheetDialog(requireContext())
        dialog?.setContentView(dialogView)

        val name = dialog?.findViewById<TextView>(R.id.bottom_sheet_name)
        val event = dialog?.findViewById<TextView>(R.id.bottom_sheet_event)
        val phoneNumber = dialog?.findViewById<TextView>(R.id.bottom_sheet_phonenumber)
        val date = dialog?.findViewById<TextView>(R.id.bottom_sheet_date)

        val editEventButton = dialog?.findViewById<Button>(R.id.bottom_sheet_details_edit_btn)
        val deleteEventButton = dialog?.findViewById<Button>(R.id.bottom_sheet_details_delete_btn)

        name?.text = item.name
        event?.text = item.event
        date?.text = item.date

        dialog?.show()

        deleteEventButton?.setOnClickListener {
            Thread {

                var reservation : Reservation? = null
                val reservationsList = reservationDb.reservationDao().getReservationList()
                for(i in reservationsList){
                    if(i.id?.equals(item.id) == true){
                        reservation = i
                    }
                }

                if (reservation != null) {
                    reservationDb.reservationDao().deleteReservation(reservation)
                }
            }.start()
            dialog?.dismiss()
            reservationsAdapter?.notifyDataSetChanged()
        }




    }
}