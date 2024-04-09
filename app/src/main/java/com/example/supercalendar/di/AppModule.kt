package com.example.supercalendar.di

import android.content.Context
import androidx.room.Room
import com.example.supercalendar.data.EventRepository
import com.example.supercalendar.data.local.EventDao
import com.example.supercalendar.data.local.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "event.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: EventDatabase): EventDao = db.eventDao()

    @Provides
    @Singleton
    fun provideRepository(dao: EventDao): EventRepository = EventRepository(dao)

}