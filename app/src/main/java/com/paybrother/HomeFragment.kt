package com.paybrother

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_reservations.*

class HomeFragment : Fragment() {

    val items = listOf(
        ReservationItem("Irina", "Kosmetolog", "July 10 2022"),
        ReservationItem("Tanja", "Nogti", "July 11 2022"),
        ReservationItem("Oksana", "Nogti", "July 12 2022"),
        ReservationItem("Igor", "Massage", "July 69 2022"),
        ReservationItem("Andrei", "Byxi4", "July 31 2022"),
        ReservationItem("Juri 1", "Project start", "July 06 2022"),
        ReservationItem("Juri 2 ", "Project start", "July 06 2022"),
        ReservationItem("Juri 3", "Project start", "July 06 2022"),
        ReservationItem("Juri 4", "Project start", "July 06 2022")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reservationsAdapter = ReservationsAdapter(items, object : ReservationsAdapter.Callback {
            override fun onItemClicked(item: ReservationItem) {

            }
        })

        reservations_rv.adapter = reservationsAdapter

    }





}