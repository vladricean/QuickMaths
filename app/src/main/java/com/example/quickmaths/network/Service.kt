package com.example.quickmaths.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://firebasestorage.googleapis.com/v0/b/quickmaths-4200f.appspot.com/o/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MathApiService {
    @GET("players.json?alt=media&token=676ad0f4-7855-490f-a315-20b199b42092")
    suspend fun getPlayerslist(): NetworkPlayerContainer
}

object DevMathNetwork {
    val retrofitService : MathApiService by lazy { retrofit.create(MathApiService::class.java) }
}