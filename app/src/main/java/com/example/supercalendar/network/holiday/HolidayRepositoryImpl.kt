package com.example.supercalendar.network.holiday

import com.example.supercalendar.domain.model.holiday.Holiday
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class HolidayRepositoryImpl(
    private val apiService: HolidayApiService
) : HolidayRepository {
    override suspend fun getHolidayList(date: String): Flow<Result<List<Holiday>>> {
        return flow {
            val holidayInfo = try {
                apiService.getHolidayInfo(date = date)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }

            emit(Result.Success(holidayInfo.result))
        }
    }

}