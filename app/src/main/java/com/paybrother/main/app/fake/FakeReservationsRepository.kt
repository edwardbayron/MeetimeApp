package com.paybrother.main.app.fake

import com.paybrother.db.Reservations
import com.paybrother.main.app.data.ReservationItem
import com.paybrother.main.app.data.ReservationUiState
import com.paybrother.main.app.repository.ReservationsRepository

class FakeReservationsRepository : ReservationsRepository {
    override fun insertReservation(reservation: ReservationItem) {}

    override fun deleteReservation(name: String) {}

    override fun updateReservation(state: ReservationUiState) {}

    override suspend fun findReservation(name: String) {}

    override fun fetchReservations(): MutableList<Reservations> {
        return mutableListOf()
    }
}