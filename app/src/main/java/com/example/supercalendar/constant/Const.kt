package com.example.supercalendar.constant

class Const {
    companion object {
        const val HOLIDAY_API_KEY = "587f81895afc08fb072c8a878b80fd77"
        const val WEATHER_API_KEY = "3c2ceb12ae10402aab20c9ba444900fb"

        const val HOLIDAY_BASE_URL = "https://apis.tianapi.com/"
        const val GEO_BASE_URL = "https://geoapi.qweather.com"
        const val WEATHER_BASE_URL = "https://api.qweather.com"

        val months = listOf(
            "一月",
            "二月",
            "三月",
            "四月",
            "五月",
            "六月",
            "七月",
            "八月",
            "九月",
            "十月",
            "十一月",
            "十二月"
        )

        val chineseNumerals = mapOf(
            1 to "周一",
            2 to "周二",
            3 to "周三",
            4 to "周四",
            5 to "周五",
            6 to "周六",
            7 to "周日"
        )

        const val NA = "?"
        const val UNKNOWN = "未知"
        const val LOADING = "_"


    }
}