package com.example.quickmaths.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "player_name_score_table")
data class Player(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo
    var playerId: Long = 0L,

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    var score: Int = 0
)
