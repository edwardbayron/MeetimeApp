package com.paybrother.room.dao

import androidx.room.*
import com.paybrother.room.Reservation

@Dao
interface ReservationDao {
    @Query("Select * from reservation")
    fun getReservationList() : List<Reservation>
    @Insert
    fun insertReservation(reservation: Reservation)
    @Update
    fun updateReservation(reservation: Reservation)
    @Delete
    fun deleteReservation(reservation: Reservation)
}