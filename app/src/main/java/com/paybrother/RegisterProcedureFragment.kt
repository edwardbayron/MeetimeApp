package com.paybrother

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paybrother.room.Reservation
import com.paybrother.room.database.ReservationDatabase
import kotlinx.android.synthetic.main.register_procedure_fragment.*
import kotlin.random.Random

class RegisterProcedureFragment : Fragment() {


    var reservationsList = emptyList<Reservation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.register_procedure_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var reservationDb : ReservationDatabase = ReservationDatabase.getInstance(requireContext())

        Thread {
            reservationsList = reservationDb.reservationDao().getReservationList()
        }.start()

        register_year.setText(arguments?.getInt("year").toString())
        register_month.setText(arguments?.getInt("month").toString())
        register_day.setText(arguments?.getInt("day").toString())

        val date = "" + register_month.text + " "+register_day.text+ " "+ register_year.text

        btn_register.setOnClickListener {
            Log.e("DAO", "i: "+number_et.text+" "+name_et.text+" "+event_et.text)
            Thread {
                reservationDb.reservationDao().insertReservation(Reservation(null, name_et.text.toString(), number_et.text.toString(), event_et.text.toString(), date))
            }.start()
            requireActivity().onBackPressed()
        }

    }
}