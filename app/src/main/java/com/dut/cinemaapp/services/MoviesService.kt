package com.dut.cinemaapp.services

import com.dut.cinemaapp.domain.Movie
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MoviesService {

    fun getMovies(): Call<List<Movie>> {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCaller::class.java)
            .getMovies()
    }

    interface ApiCaller {
        @GET("movies/all")
        fun getMovies(): Call<List<Movie>>
    }
}