//package com.paybrother.old.room.dao
//
//import androidx.room.*
//import com.paybrother.old.room.Reservation
//
//@Dao
//interface ReservationDao {
//    @Query("Select * from reservation")
//    fun getReservationList() : List<Reservation>
//    @Insert
//    fun insertReservation(reservation: Reservation)
//    @Update
//    fun updateReservation(reservation: Reservation)
//    @Delete
//    fun deleteReservation(reservation: Reservation)
//}