package com.paybrother.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Reservations::class)], version = 3)
abstract class MeetimeRoomDatabase : RoomDatabase() {

    abstract fun reservationsDao(): ReservationsDao

    companion object {
        private const val DB_NAME = "meetimeDb"

//        @Volatile
//        private var INSTANCE: MeetimeRoomDatabase? = null

//        fun getInstance(context: Context): MeetimeRoomDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        MeetimeRoomDatabase::class.java,
//                        DB_NAME
//                    ).fallbackToDestructiveMigration().build()
//
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
    }
}