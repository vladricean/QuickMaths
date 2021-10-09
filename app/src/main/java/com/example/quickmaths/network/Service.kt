package com.example.quickmaths.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface DevMathService {
    @GET("players.json")
    suspend fun getPlayerslist(): NetworkPlayerContainer
}

object DevMathNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/vladricean/4b40386fe58cae241831d9ce4a993366/raw/9c0089cf15d77ceb4301af2405ccb15290b9d98a/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val devmaths = retrofit.create(DevMathService::class.java)

}

