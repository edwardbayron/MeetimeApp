package com.paybrother.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paybrother.db.Reservations

@Dao
interface ReservationsDao {
    @Query("Select * from reservation")
    fun getReservationList() : LiveData<List<Reservations>>
    @Insert
    fun insertReservation(reservation: Reservations)
    @Update
    fun updateReservation(reservation: Reservations)
    @Query("SELECT * FROM reservation WHERE name = :name")
    fun findReservation(name: String): List<Reservations>
    @Query("DELETE FROM reservation WHERE name = :name")
    fun deleteReservation(name: String)
}