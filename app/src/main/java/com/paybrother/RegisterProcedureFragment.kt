package com.paybrother

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.paybrother.databinding.FragmentHomeBinding
import com.paybrother.databinding.FragmentReservationsBinding
import com.paybrother.databinding.RegisterProcedureFragmentBinding
import com.paybrother.room.Reservation
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.register_procedure_fragment.*
import kotlin.random.Random

@AndroidEntryPoint
class RegisterProcedureFragment : Fragment() {

    private val viewModel : HomeFragmentViewModel by activityViewModels()

    lateinit var binding : RegisterProcedureFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegisterProcedureFragmentBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = requireActivity()
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_year.setText(arguments?.getInt("year").toString())
        register_month.setText(arguments?.getInt("month").toString())
        register_day.setText(arguments?.getInt("day").toString())

        val date = "" + register_month.text + " "+register_day.text+ " "+ register_year.text

        btn_register.setOnClickListener {
//            Log.e("DAO", "i: "+number_et.text+" "+name_et.text+" "+event_et.text)
//            Thread {
//                roomDb?.reservationDao()?.insertReservation(Reservation(null, name_et.text.toString(), number_et.text.toString(), event_et.text.toString(), date))
//            }.start()
            viewModel.insertProcedure(Reservation(null, name_et.text.toString(), number_et.text.toString(), event_et.text.toString(), date))
            requireActivity().onBackPressed()

        }

        viewModel.proceduresList.observe(viewLifecycleOwner){
            for(i in it){
                Log.wtf("TAG", "register i: "+i.name)
            }

        }

    }

}