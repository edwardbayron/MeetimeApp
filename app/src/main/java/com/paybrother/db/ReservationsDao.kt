package com.paybrother.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReservationsDao {

    @Query("Select * from reservation")
    fun getReservationList() : MutableList<Reservations>

    @Query("INSERT into reservation VALUES (" +
            ":id, " +
            ":name, " +
            ":phoneNumber, " +
            ":event, " +
            ":date, " +
            ":time, " +
            ":notificationText, " +
            ":notificationTime)")
    fun insertReservation(
        id: Long?,
        name: String,
        phoneNumber: String,
        event: String,
        date: String,
        time: String,
        notificationText: String,
        notificationTime: String)

    @Query("UPDATE reservation SET name = :name WHERE id = :id")
    fun updateReservation(id: Long?, name: String)

    @Query("SELECT * FROM reservation WHERE name = :name")
    fun findReservation(name: String): List<Reservations>

    @Query("DELETE FROM reservation WHERE name = :name")
    fun deleteReservation(name: String)
}