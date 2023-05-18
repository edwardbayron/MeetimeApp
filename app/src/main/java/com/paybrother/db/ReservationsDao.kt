package com.paybrother.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paybrother.db.Reservations

@Dao
interface ReservationsDao {

    @Query("Select * from reservation")
    fun getReservationList() : LiveData<List<Reservations>>

    @Query("INSERT into reservation VALUES (:id, :name, :phoneNumber, :event, :date)")
    fun insertReservation(id : Long, name: String, phoneNumber: String, event: String, date: String)

    @Query("UPDATE reservation SET name = :name WHERE id = :id")
    fun updateReservation(id: Long?, name: String)

    @Query("SELECT * FROM reservation WHERE name = :name")
    fun findReservation(name: String): List<Reservations>

    @Query("DELETE FROM reservation WHERE name = :name")
    fun deleteReservation(name: String)
}