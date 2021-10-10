package com.example.quickmaths.network

import com.example.quickmaths.database.DatabasePlayer
import com.example.quickmaths.domain.DomainPlayer
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPlayerContainer(val players: List<NetworkPlayer>)

@JsonClass(generateAdapter = true)
data class NetworkPlayer(
    val id: Long,
    val name: String,
    val score: Int
)

/**
 * Convert Network results to domain objects
 */
fun NetworkPlayerContainer.asDomainModel(): List<DomainPlayer> {
    return players.map{
        DomainPlayer(
            id = it.id,
            name = it.name,
            score = it.score
        )
    }
}

/**
 * Convert Network results to database objects
 */
fun NetworkPlayerContainer.asDatabaseModel(): List<DatabasePlayer> {
    return players.map{
        DatabasePlayer(
            id = it.id,
            name = it.name,
            score = it.score
        )
    }
}