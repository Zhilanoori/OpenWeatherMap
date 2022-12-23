package com.noori.openweathermapapi6.api

import com.noori.openweathermapapi6.data.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeatherData(
        @Query("q") cityName:String = "Tehran",
        @Query("appid") API_KEY:String ="06b8274ae32322d1af93bf3214f7eb17",
        @Query("units") units:String = "metric"
    ):Call<WeatherData>
}