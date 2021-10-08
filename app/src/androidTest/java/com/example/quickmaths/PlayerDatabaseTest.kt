package com.example.quickmaths

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.quickmaths.database.PlayerDatabaseDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class PlayerDatabaseTest {

    private lateinit var playerDao : PlayerDatabaseDao
    private lateinit var db: PlayerDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, PlayerDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        playerDao = db.playerDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
//        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPlayer() {
        val player = Player()
        playerDao.insert(player)
        val somePlayer = playerDao.getPlayer()
        assertEquals(somePlayer?.score, null)
    }
}


