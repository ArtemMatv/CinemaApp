package com.dut.cinemaapp.domain

data class NewReview(
    val text: String,
    val movieId: Long,
    val authorId: Long
)