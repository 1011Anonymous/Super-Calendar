package com.example.supercalendar.di

import android.content.Context
import androidx.room.Room
import com.example.supercalendar.constant.Const
import com.example.supercalendar.data.EventRepository
import com.example.supercalendar.data.local.EventDao
import com.example.supercalendar.data.local.EventDatabase
import com.example.supercalendar.network.holiday.HolidayApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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

    @Provides
    @Singleton
    fun provideHolidayApi(): HolidayApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.HOLIDAY_BASE_URL)
            .client(client)
            .build()
            .create(HolidayApiService::class.java)
    }

}