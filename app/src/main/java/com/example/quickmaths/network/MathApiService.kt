package com.example.quickmaths.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://gist.githubusercontent.com/vladricean/4b40386fe58cae241831d9ce4a993366/raw/9c0089cf15d77ceb4301af2405ccb15290b9d98a/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MathApiService {
    @GET("players.json")
    suspend fun getPlayersFromNetwork(): List<PlayerNetwork>
}

object MathApi {
    val retrofitService : MathApiService by lazy { retrofit.create(MathApiService::class.java) }
}