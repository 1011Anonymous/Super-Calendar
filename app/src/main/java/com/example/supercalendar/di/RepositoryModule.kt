package com.example.supercalendar.di

import com.example.supercalendar.network.holiday.HolidayRepository
import com.example.supercalendar.network.holiday.HolidayRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHolidayRepository(
        holidayRepositoryImpl: HolidayRepositoryImpl
    ): HolidayRepository
}