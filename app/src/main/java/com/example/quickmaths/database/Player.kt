package com.example.quickmaths.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "player_name_score_table")
data class Player(
    @PrimaryKey(autoGenerate = true)
    var playerId: Long = 0,

    @ColumnInfo
    var name: String = "Ema",

    @ColumnInfo
    var score: Int = 0
)
