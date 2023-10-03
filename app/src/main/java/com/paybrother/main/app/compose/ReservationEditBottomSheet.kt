package com.paybrother.main.app.compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paybrother.R
import com.paybrother.databinding.FragmentReservationEditBinding
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationEditBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentReservationEditBinding? = null
    private val binding: FragmentReservationEditBinding get() = _binding!!

    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme_Medium)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationEditBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val viewModel = hiltViewModel<LoanViewModel>()
                val uiState = viewModel.uiState.collectAsState()
                Log.e("MEETIME", "intent uiState.name: "+uiState.value.name)
                Log.e("MEETIME", "intent: "+arguments?.getString("name"))

                arguments?.let {
                    viewModel.selectedReservation(uiState.value.copy(
                        id = it.getLong("id"),
                        name = it.getString("name").toString(),
                        phoneNumber = it.getString("phoneNumber").toString(),
                        event = it.getString("event").toString(),
                        date = it.getString("date").toString()
                    ))
                }

                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column {
                            val reservationTitleText = remember { mutableStateOf(uiState.value.name) }
                            val reservationPhoneNumberText = remember { mutableStateOf(uiState.value.phoneNumber) }
                            val reservationEventText = remember { mutableStateOf(uiState.value.event) }
                            val reservationDateText = remember { mutableStateOf(uiState.value.date) }

                            Log.e("MEETIME", "intent uiState.name: "+uiState.value.name)
                            Log.e("MEETIME", "intent uiState.phoneNumber: "+uiState.value.phoneNumber)
                            Log.e("MEETIME", "intent uiState.event: "+uiState.value.event)
                            Log.e("MEETIME", "intent uiState.date: "+uiState.value.date)

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 20.dp)
                            ) {

                                Box(Modifier.fillMaxSize()) {
                                    Column {
                                        TextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            value = reservationTitleText.value,
                                            onValueChange = {
                                                reservationTitleText.value = it
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.Black,
                                                containerColor = Color.White
                                            )
                                        )

                                        TextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            value = reservationPhoneNumberText.value,
                                            onValueChange = {
                                                reservationPhoneNumberText.value = it
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.Black,
                                                containerColor = Color.White
                                            )
                                        )

                                        TextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            value = reservationEventText.value,
                                            onValueChange = {
                                                reservationEventText.value = it
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.Black,
                                                containerColor = Color.White
                                            )
                                        )

                                        TextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            value = reservationDateText.value,
                                            onValueChange = {
                                                reservationDateText.value = it
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.Black,
                                                containerColor = Color.White
                                            )
                                        )

                                        TextButton(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = {
                                                //onSavePress(uiState)
                                                //onSavePress()
                                                viewModel.updateReservation(state = uiState.value)
                                                viewModel.selectedReservation(uiState.value.copy(uiState.value.id, uiState.value.name, uiState.value.phoneNumber, uiState.value.event, uiState.value.date))
                                                this@ReservationEditBottomSheet.dismiss()
                                            }) {

                                            Text("Save")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return view
    }
}

