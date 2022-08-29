package com.paybrother

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.orhanobut.logger.Logger
import com.paybrother.databinding.FragmentHomeBinding
import com.paybrother.room.Reservation
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    var reservationItems = arrayListOf<ReservationItem>()

    var reservationsAdapter: ReservationsAdapter? = null
    private var dialog: BottomSheetDialog? = null

    private var roomDb: ReservationDatabase? = null

    lateinit var binding : FragmentHomeBinding
    private val viewModel : HomeFragmentViewModel by activityViewModels()

    private var onItemClickListener = object : ReservationsAdapter.Callback {
        override fun onItemClicked(item: ReservationItem) {
            roomDb?.let {
                setupProcedureDetailsDialog(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Home"
        roomDb = ReservationDatabase.getInstance(requireContext())

        setupAdapter()
        setupDataListeners()

    }

    private fun setupDataListeners(){
        viewModel.proceduresList.observe(viewLifecycleOwner, Observer{
            for(i in it){
                Log.wtf("TAG: ", "observer i: "+i.name)
            }
            reservationsAdapter?.setData(it)

        })
    }

    private fun setupAdapter() {
        reservationsAdapter = ReservationsAdapter(reservationItems, onItemClickListener)
        binding.reservationsRv.adapter = reservationsAdapter
    }

    private fun setupProcedureDetailsDialog(
        item: ReservationItem
    ) {
        val dialogView =
            requireActivity().layoutInflater.inflate(R.layout.bottom_sheet_procedure_details, null)
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
//            Thread {
//
//                var reservation: Reservation? = null
//                val reservationsList = roomDb?.reservationDao()?.getReservationList()
//                for (i in reservationsList!!) {
//                    if (i.id?.equals(item.id) == true) {
//                        reservation = i
//                    }
//                }
//
//                if (reservation != null) {
//                    roomDb?.reservationDao()?.deleteReservation(reservation)
//                }
//            }.start()

            viewModel.deleteProcedure(item.id)
            dialog?.dismiss()
            reservationsAdapter?.notifyDataSetChanged()
        }

        editEventButton?.setOnClickListener{
            val editFragment = ProcedureEditFragment()
            val args = Bundle()
            args.putString("name", name?.text.toString())
            args.putString("event", event?.text.toString())
            args.putString("date", date?.text.toString())

            editFragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.parent_container, editFragment).commit()
            dialog?.dismiss()
        }


    }
}