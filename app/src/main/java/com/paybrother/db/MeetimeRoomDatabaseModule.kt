package com.paybrother.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

class MeetimeRoomDatabaseModule {

    @Provides
    fun provideReservationsDao(database: MeetimeRoomDatabase) : ReservationsDao{
        return database.reservationsDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : MeetimeRoomDatabase{
        return Room.databaseBuilder(context, MeetimeRoomDatabase::class.java, "meetimeDb")
            .fallbackToDestructiveMigration()
            .build()
    }
}