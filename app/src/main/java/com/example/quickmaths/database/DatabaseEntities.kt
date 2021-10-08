package com.example.quickmaths.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quickmaths.domain.DomPlayer

@Entity
data class DatabasePlayer constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    val score: Int
)

fun List<DatabasePlayer>.asDomaninModel(): List<DomPlayer> {
    return map {
        DomPlayer(
            id = it.id,
            name = it.name,
            score = it.score
        )
    }
}