package com.example.quickmaths.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlayerDatabaseDao{
    @Insert
    fun insert(player: Player)

    @Update
    fun update(player: Player)

    @Query("SELECT * from player_name_score_table WHERE playerId = :key")
    fun get(key: Long): Player?

    @Query("DELETE FROM player_name_score_table")
    fun clear()

    // First player created
    @Query("SELECT * FROM player_name_score_table ORDER BY playerId DESC LIMIT 1")
    fun getPlayer(): Player?

    @Query("SELECT * FROM player_name_score_table ORDER BY playerId DESC")
    fun getAllPlayers(): LiveData<List<Player>>
}