package com.paybrother.main.app.compose

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.versionedparcelable.ParcelField


@Stable
class ReservationUiStateItem(
    id: Long?,
    name: String,
    phoneNumber: String,
    event: String,
     date: String,
     time: String,
     notificationText: String,
     notificationTime: String,
){
    var id = mutableStateOf(id)
    var name = mutableStateOf(name)
    var phoneNumber = mutableStateOf(phoneNumber)
    var event = mutableStateOf(event)
    var date = mutableStateOf(date)
    var time = mutableStateOf(time)
    var notificationText = mutableStateOf(notificationText)
    var notificationTime = mutableStateOf(notificationTime)
}