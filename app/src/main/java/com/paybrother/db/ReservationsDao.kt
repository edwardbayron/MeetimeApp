package com.paybrother.db

import androidx.room.*
import com.paybrother.db.Reservations

@Dao
interface ReservationsDao {
    @Query("Select * from reservation")
    fun getReservationList() : List<Reservations>
    @Insert
    fun insertReservation(reservation: Reservations)
    @Update
    fun updateReservation(reservation: Reservations)
    @Delete
    fun deleteReservation(reservation: Reservations)
}