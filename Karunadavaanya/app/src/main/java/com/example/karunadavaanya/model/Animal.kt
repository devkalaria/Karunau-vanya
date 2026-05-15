package com.example.karunadavaanya.model

import java.io.Serializable

data class Animal(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val fullDescription: String,
    val habitat: String,
    val diet: String,
    val conservationStatus: String,
    val interestingFact: String,
    val safetyMeasures: String,
    val imageResourceId: Int
) : Serializable
