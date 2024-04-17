package com.example.supercalendar.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton

val Context.myPreferencesDataStore: DataStore<Preferences> by preferencesDataStore("Settings")

data class SettingStatus(
    val isDarkTheme: String,
    val isHoliday: Boolean,
    val isLunar: Boolean,
    val isFestival: Boolean,
    val isWeekday: Boolean,
    val firstDayOfWeek: String,
    val isHighlight: Boolean,
    val isHideWeather: Boolean,
)

@Singleton
class StoreUserSettings @Inject constructor(@ApplicationContext context: Context) {

    private val myPreferencesDataStore = context.myPreferencesDataStore

    private object PreferencesKeys {
        val IS_DARK_KEY = stringPreferencesKey("is_dark")
        val IS_HOLIDAY_KEY = booleanPreferencesKey("is_holiday")
        val IS_LUNAR_KEY = booleanPreferencesKey("is_lunar")
        val IS_FESTIVAL_KEY = booleanPreferencesKey("is_festival")
        val IS_WEEKDAY_KEY = booleanPreferencesKey("is_weekday")
        val FIRST_DAY_OF_WEEK_KEY = stringPreferencesKey("first_day_of_week")
        val IS_HIGHLIGHT_KEY = booleanPreferencesKey("is_highlight")
        val IS_HIDE_WEATHER_KEY = booleanPreferencesKey("is_hide_weather")
    }


    val settingStatusFlow = myPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val isDark = preferences[PreferencesKeys.IS_DARK_KEY] ?: "跟随系统"
            val isHighlight = preferences[PreferencesKeys.IS_HIGHLIGHT_KEY] ?: false
            val isHoliday = preferences[PreferencesKeys.IS_HOLIDAY_KEY] ?: false
            val isLunar = preferences[PreferencesKeys.IS_LUNAR_KEY] ?: false
            val isFestival = preferences[PreferencesKeys.IS_FESTIVAL_KEY] ?: false
            val isWeekday = preferences[PreferencesKeys.IS_WEEKDAY_KEY] ?: false
            val isHideWeather = preferences[PreferencesKeys.IS_HIDE_WEATHER_KEY] ?: false
            val firstDayOfWeek = preferences[PreferencesKeys.FIRST_DAY_OF_WEEK_KEY] ?: "周一"

            SettingStatus(
                isDark,
                isHoliday,
                isLunar,
                isFestival,
                isWeekday,
                firstDayOfWeek,
                isHighlight,
                isHideWeather
            )
        }

    suspend fun updateIsDark(isDark: String) {
        myPreferencesDataStore.edit {preferences ->
            preferences[PreferencesKeys.IS_DARK_KEY] = isDark
        }
    }

    suspend fun updateIsHoliday(isHoliday: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_HOLIDAY_KEY] = isHoliday
        }
    }

    suspend fun updateIsLunar(isLunar: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LUNAR_KEY] = isLunar
        }
    }

    suspend fun updateIsFestival(isFestival: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FESTIVAL_KEY] = isFestival
        }
    }

    suspend fun updateIsWeekDay(isWeekDay: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_WEEKDAY_KEY] = isWeekDay
        }
    }

    suspend fun updateFirstDayOfWeek(dayOfWeek: DayOfWeek) {
        myPreferencesDataStore.edit {preferences ->
            preferences[PreferencesKeys.FIRST_DAY_OF_WEEK_KEY] = when(dayOfWeek) {
                DayOfWeek.MONDAY -> "周一"
                DayOfWeek.SATURDAY -> "周六"
                DayOfWeek.SUNDAY -> "周日"
                else -> "周一"
            }
        }
    }

    suspend fun updateIsHighlight(isHighlight: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_HIGHLIGHT_KEY] = isHighlight
        }
    }

    suspend fun updateIsHideWeather(isHideWeather: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_HIDE_WEATHER_KEY] = isHideWeather
        }
    }

}