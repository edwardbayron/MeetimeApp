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

    @Query("UPDATE reservation SET name = :name")
    fun updateReservation(name: String)

    @Query("SELECT * FROM reservation WHERE name = :name")
    fun findReservation(name: String): List<Reservations>

    @Query("DELETE FROM reservation WHERE name = :name")
    fun deleteReservation(name: String)
}