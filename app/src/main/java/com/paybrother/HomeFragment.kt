package com.paybrother

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                Thread {
                    reservationDb.reservationDao().deleteReservation(Reservation(item.id, item.name, "", item.event, item.date))
                }.start()



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

    private fun setupProcedureDetailsDialog(){
        val dialogView = requireActivity().layoutInflater.inflate(R.layout.bottom_sheet_procedure_details, null)

    }
}