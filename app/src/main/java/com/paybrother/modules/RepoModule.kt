package com.paybrother.modules

import com.paybrother.db.ReservationsDao
import com.paybrother.main.app.repository.ReservationsRepository
import com.paybrother.main.app.repository.ReservationsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(value = [SingletonComponent::class])
@Module
class RepoModule {

    @Provides
    fun providesReservationsRepository(
        reservationsDao: ReservationsDao
    ): ReservationsRepository {
        return ReservationsRepositoryImpl(reservationsDao)
    }

}