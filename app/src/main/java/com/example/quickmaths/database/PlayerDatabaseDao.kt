package com.example.quickmaths.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDatabaseDao{
    @Insert
    suspend fun insert(player: Player)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(players: List<Player>)

    @Update
    suspend fun update(player: Player)

    @Query("SELECT * from player_name_score_table WHERE playerId = :key")
    suspend fun get(key: Long): Player?

    @Query("DELETE FROM player_name_score_table")
    suspend fun clear()

    // New player created
    @Query("SELECT * FROM player_name_score_table ORDER BY playerId DESC LIMIT 1")
    suspend fun getPlayer(): Player?

    // no need for suspend because i am using LiveData
    @Query("SELECT * FROM player_name_score_table ORDER BY playerId DESC")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT COUNT(*) FROM player_name_score_table")
    suspend fun getNumberOfPlayers(): Int?
}