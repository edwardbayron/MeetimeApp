package com.paybrother.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.paybrother.room.Reservation
import com.paybrother.room.dao.ReservationDao

@Database(entities = [Reservation::class], exportSchema = false, version = 1)
abstract class ReservationDatabase : RoomDatabase() {
    companion object{

        private const val DB_NAME = "reservation_db"
        var instance: ReservationDatabase? = null

        fun getInstance(context: Context): ReservationDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext, ReservationDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
            }
            return instance as ReservationDatabase
        }
    }


    abstract fun reservationDao(): ReservationDao
}