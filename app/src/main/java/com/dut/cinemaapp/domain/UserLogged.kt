package com.dut.cinemaapp.domain

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class UserLogged(
    val userId: Long,
    val displayName: String
)