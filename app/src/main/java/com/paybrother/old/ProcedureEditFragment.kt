package com.paybrother.old//package com.paybrother
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import com.paybrother.databinding.FragmentProcedureEditBinding
//import com.paybrother.old.room.Reservation
//import dagger.hilt.android.AndroidEntryPoint
//
//
//class ProcedureEditFragment : Fragment() {
//
//    private var _binding : FragmentProcedureEditBinding? = null
//    private val binding : FragmentProcedureEditBinding? get() = _binding
//
//    private val viewModel : HomeFragmentViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentProcedureEditBinding.inflate(inflater, container, false)
//        return binding?.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        requireActivity().title = "Edit procedure"
//
//        val id = arguments?.getLong("id")
//        val name = arguments?.getString("name")
//        val event = arguments?.getString("event")
//        //val phoneNumber = dialog?.findViewById<TextView>(R.id.bottom_sheet_phonenumber)
//        val date = arguments?.getString("date")
//
//
//        binding?.procedureEditNameEt?.setText(name)
//        binding?.procedureEditEventEt?.setText(event)
//        binding?.procedureEditDateEt?.setText(date)
//
//
//
//
//        binding?.procedureEditConfirmBtn?.setOnClickListener {
//            val editedName : String = binding?.procedureEditNameEt?.text.toString()
//            val editedEvent : String = binding?.procedureEditEventEt?.text.toString()
//            val editedDate : String = binding?.procedureEditDateEt?.text.toString()
//            val reservation = Reservation(id, editedName, "", editedEvent, editedDate)
//            viewModel.editProcedure(reservation)
//            requireActivity().onBackPressed()
//        }
//
//        binding?.procedureEditCancelBtn?.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//
//
//
//
//    }
//
//}