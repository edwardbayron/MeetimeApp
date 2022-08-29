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
        requireActivity().title = "Create new procedure"

        binding.registerYear.setText(arguments?.getInt("year").toString())
        binding.registerMonth.setText(arguments?.getInt("month").toString())
        binding.registerDay.setText(arguments?.getInt("day").toString())

        val date = "" + binding.registerMonth.text + " "+binding.registerDay.text+ " "+ binding.registerYear.text

        binding.btnRegister.setOnClickListener {
            viewModel.insertProcedure(Reservation(null, binding.nameEt.text.toString(), binding.numberEt.text.toString(), binding.eventEt.text.toString(), date))
            requireActivity().onBackPressed()
        }

        // test commit through gitkraken
    }
}