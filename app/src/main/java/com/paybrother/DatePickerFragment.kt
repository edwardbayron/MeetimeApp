package com.paybrother

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        //sendDatePicked(p1, p2, p3,)



        val bundle = Bundle()
        bundle.putInt("year", year)
        bundle.putInt("month", month)
        bundle.putInt("day", day)
        val registerPickerFragment = RegisterProcedureFragment()
        registerPickerFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(HomeFragment::class.java.name)
            ?.replace(R.id.parent_container, registerPickerFragment)?.commit()


    }


    @SuppressLint("RestrictedApi")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//
//        DatePickerDialog(activity, this, year, month, day)
//            .setButton(DialogInterface.BUTTON_POSITIVE, "ACCEPT", object : DialogInterface.OnClickListener{
//            override fun onClick(p0: DialogInterface?, p1: Int) {
//                Log.e("button pressed", "lol")
//                //year = intent.extras.getInt("year")
////                val intent = Intent(activity, HomeActivity::class.java)
////                intent
////                    .putExtra("year", year)
////                    .putExtra("month", month)
////                    .putExtra("day", day)
////                startActivity(intent)
//
//



//
//
//
//            }
//
//        })


        return DatePickerDialog(requireActivity(), this, year, month, day)
    }



}