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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
                var uiState = ReservationUiState(0L, "TEST", "", "", "")
                Log.e("MEETIME", "intent: "+arguments?.getString("name"))
                arguments?.let {
                    uiState = ReservationUiState(
                        id = it.getLong("id"),
                        name = it.getString("name").toString(),
                        phoneNumber = it.getString("phoneNumber").toString(),
                        event = it.getString("event").toString(),
                        date = it.getString("date").toString()
                    )
                }




                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        ReservationEditContainer(uiState = uiState, viewModel = viewModel)
                    }
                }
            }
        }

        return view
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun ReservationEditContainer(uiState: ReservationUiState, viewModel: LoanViewModel){
        ReservationEditContainer(
            uiState,
            onSavePress = {
                viewModel.updateReservation(state = uiState)
                this.dismiss()
            }
        )
    }

    @Composable
    fun ReservationEditContainer(
        uiState: ReservationUiState,
        onSavePress: () -> Unit
    ) {
        Column {
            ReservationEditDetails(uiState, onSavePress)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ReservationEditDetails(
        uiState: ReservationUiState,
        onSavePress: () -> Unit
    ) {
        val reservationTitleText = remember { mutableStateOf(uiState.name) }
        val reservationPhoneNumberText = remember { mutableStateOf(uiState.phoneNumber) }
        val reservationEventText = remember { mutableStateOf(uiState.event) }
        val reservationDateText = remember { mutableStateOf(uiState.date) }

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
                            onSavePress()
                        }) {

                        Text("Save")
                    }
                }
            }
        }
    }
}

