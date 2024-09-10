package dev.venc.nbasample.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.log
import dev.venc.nbasample.repository.api.PlayerApiDataSource.getPlayerFromApi
import dev.venc.nbasample.repository.api.PlayerApiDataSource.getPlayersFromApi
import dev.venc.nbasample.repository.datamodel.Player
import dev.venc.nbasample.repository.db.PlayerDatabaseDataSource.getPlayerFromDatabase
import dev.venc.nbasample.repository.db.PlayerDatabaseDataSource.getPlayersFromDatabase
import dev.venc.nbasample.repository.db.PlayerDatabaseDataSource.savePlayersToDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NBARepository {

    suspend fun getPlayer(id: Int): Player = withContext(Dispatchers.IO) {
        getPlayerFromDatabase(id)?.let {
            return@withContext it
        }
        val playerFromApi = getPlayerFromApi(id)
        launch { savePlayersToDatabase(playerFromApi) }
        Log.w("SAVED PLAYER", playerFromApi.playerId.toString())
        return@withContext playerFromApi
    }

    suspend fun getPayers(startingId: Int, amount: Int): List<Player> = withContext(Dispatchers.IO) {
        val listFromDatabase = getPlayersFromDatabase(startingId, amount)
        if (listFromDatabase.size == amount) {
            return@withContext listFromDatabase
        }
        val playerFromApi = getPlayersFromApi(startingId, amount)
        launch { savePlayersToDatabase(*playerFromApi.toTypedArray()) }
        Log.w("SAVED PLAYER FROM", playerFromApi[0].playerId.toString())
        Log.w("SAVED PLAYER TO", playerFromApi.last().playerId.toString())
        return@withContext playerFromApi
    }

    fun playersPagingSource(): PagingSource<Int, Player> {
        return PlayersPagingSource(this)
    }


}