package dev.venc.nbasample.repository.db

import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlayerDatabaseDataSource {
    suspend fun getPlayerFromDatabase(id: Int): Player? = withContext(Dispatchers.IO) {
        return@withContext PlayerDatabase.getInstance().playerDao().getById(id)
    }

    suspend fun getPlayersFromDatabase(startingId: Int, amount: Int): List<Player> = withContext(Dispatchers.IO) {
        return@withContext PlayerDatabase.getInstance().playerDao().getMultipleFromId(startingId, amount)
    }

    suspend fun savePlayersToDatabase(vararg player: Player) = withContext(Dispatchers.IO) {
        PlayerDatabase.getInstance().playerDao().insertAll(*player)
    }
}