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
    fun loadById(playerId: Int): Player?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: Player)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: Player)

    @Delete
    fun delete(player: Player)
}