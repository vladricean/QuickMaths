package com.example.quickmaths.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: DatabasePlayer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(players: List<DatabasePlayer>)

    @Update
    suspend fun update(player: DatabasePlayer)

    @Query("SELECT * from databaseplayer WHERE id = :key")
    suspend fun get(key: Long): DatabasePlayer?

    @Query("DELETE FROM databaseplayer")
    suspend fun clear()

    // New player created
    @Query("SELECT * FROM databaseplayer ORDER BY id DESC LIMIT 1")
    suspend fun getPlayer(): DatabasePlayer?

    // no need for suspend because i am using LiveData
    @Query("SELECT * FROM databaseplayer ORDER BY id DESC")
    fun getAllPlayers(): LiveData<List<DatabasePlayer>>

    @Query("SELECT COUNT(*) FROM databaseplayer")
    suspend fun getNumberOfPlayers(): Int?
}

@Database(entities = [DatabasePlayer::class], version = 1)
abstract class PlayersDatabase : RoomDatabase() {
    abstract val playerDao: PlayerDao
}

private lateinit var INSTANCE: PlayersDatabase

fun getDatabase(context: Context): PlayersDatabase {
    synchronized(PlayersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PlayersDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}
