package com.dut.cinemaapp.services

import com.dut.cinemaapp.domain.Session
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class SessionsService {

    fun getSessions(): Call<List<Session>> {

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCaller::class.java)
            .getSessions()
    }

    interface ApiCaller {
        @GET("sessions/actual")
        fun getSessions(): Call<List<Session>>
    }
}