package com.paybrother.main.app.data

data class ReservationUiState(
    var id: Long?,
    var name: String,
    var phoneNumber: String,
    var event: String,
    var date: String,
    var time: String,
    var notificationText: String,
    var notificationTime: String,
)