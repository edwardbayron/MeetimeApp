package com.paybrother.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reservations::class], exportSchema = false, version = 1)
abstract class RoomDatabase : RoomDatabase() {
    companion object {

        private const val DB_NAME = "meetimeDb"

        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getInstance(context: Context): RoomDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }


        }
    }


    abstract fun reservationDao(): ReservationsDao
    //abstract fun contactsDao(): ContactsDao
}