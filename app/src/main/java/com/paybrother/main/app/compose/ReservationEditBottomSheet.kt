package com.paybrother.main.app.compose

import android.os.Bundle
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paybrother.R
import com.paybrother.databinding.FragmentReservationEditBinding
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.viewmodels.LoanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationEditBottomSheet(val viewModel: LoanViewModel) : BottomSheetDialogFragment() {

    private var _binding: FragmentReservationEditBinding? = null
    private val binding: FragmentReservationEditBinding get() = _binding!!

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
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()

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
                            val reservationTimeText = remember { mutableStateOf(uiState.value.time) }
                            val reservationNotificationText = remember { mutableStateOf(uiState.value.notificationText) }
                            val reservationNotificationTime = remember { mutableStateOf(uiState.value.notificationTime) }

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
                                                viewModel.updateReservation(
                                                    state = ReservationUiState(
                                                        id = uiState.value.id,
                                                        name = reservationTitleText.value,
                                                        phoneNumber = reservationPhoneNumberText.value,
                                                        event = reservationEventText.value,
                                                        date = reservationDateText.value,
                                                        time = reservationTimeText.value,
                                                        notificationText = reservationNotificationText.value,
                                                        notificationTime = reservationNotificationTime.value
                                                    ))
                                                viewModel.selectedReservation(
                                                    uiState.value.copy(
                                                        uiState.value.id,
                                                        reservationTitleText.value,
                                                        reservationPhoneNumberText.value,
                                                        reservationEventText.value,
                                                        reservationDateText.value))
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

