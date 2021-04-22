package com.dut.cinemaapp.services

import com.dut.cinemaapp.domain.Session
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class SessionsService {

    fun getSessions(): Call<List<Session>> {

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCaller::class.java)
            .getSessions("Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrdmF6YXIyNTY5QGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNjE5MTE0NzY5LCJleHAiOjE2MTkxMTgzNjl9.NiZQejEjZRD-kDNXVKMc0Vwm5nGWZkgfHQMVdyXpCuE")
    }

    interface ApiCaller {
        @GET("sessions/actual")
        fun getSessions(@Header("Authorization") token: String): Call<List<Session>>
    }
}