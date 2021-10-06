package com.example.quickmaths.network

import com.squareup.moshi.Json


data class PlayerNetwork(
    val id: Int,
    val name: String,
    val score: Int
)