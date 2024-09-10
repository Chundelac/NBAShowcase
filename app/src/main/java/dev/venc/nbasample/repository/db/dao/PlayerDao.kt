package dev.venc.nbasample.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.venc.nbasample.repository.datamodel.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getAll(): List<Player>

    @Query("SELECT * FROM players WHERE playerId IN (:playerId) LIMIT 1")
    fun getById(playerId: Int): Player?

    @Query("SELECT * FROM players WHERE playerId >= :playerId LIMIT :limit")
    fun getMultipleFromId(playerId: Int, limit: Int): List<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: Player)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: Player)

    @Delete
    fun delete(player: Player)
}