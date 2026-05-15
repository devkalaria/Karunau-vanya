package com.example.karunadavaanya.model

data class Alert(
    val animalName: String = "",
    val description: String = "",
    val location: String = "",
    val district: String = "",
    val timestamp: String = "",
    val createdAt: Long = 0 // Timestamp in milliseconds for expiration logic
)
