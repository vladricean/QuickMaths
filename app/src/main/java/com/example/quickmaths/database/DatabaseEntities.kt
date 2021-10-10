package com.example.quickmaths.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quickmaths.domain.DomainPlayer

@Entity
data class DatabasePlayer constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    val score: Int
)

fun List<DatabasePlayer>.asDomaninModel(): List<DomainPlayer> {
    return map {
        DomainPlayer(
            id = it.id,
            name = it.name,
            score = it.score
        )
    }
}