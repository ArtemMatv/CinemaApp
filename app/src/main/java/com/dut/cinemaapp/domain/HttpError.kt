package com.dut.cinemaapp.domain

data class HttpError(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
