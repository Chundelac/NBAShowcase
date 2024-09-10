package dev.venc.nbasample.repository

import androidx.paging.PagingSource
import dev.venc.nbasample.repository.api.NBARetrofitService
import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NBARepository {

    suspend fun getPlayer(id: Int): Player = withContext(Dispatchers.IO) {
        val response = NBARetrofitService.nbaApi.getPlayer(id)

        if (response.isSuccessful) {
            return@withContext response.body()?.data!!
        }

        throw (Exception("Player not found")) //TODO better error handling
    }

    suspend fun getAllPayers(): List<Player>? = withContext(Dispatchers.IO) {
        val response = NBARetrofitService.nbaApi.getAllPlayer()

        if (response.isSuccessful) {
            return@withContext response.body()?.data
        }

        return@withContext null
    }

    suspend fun getPayers(startingId: Int, amount: Int): List<Player> = withContext(Dispatchers.IO) {
        val response = NBARetrofitService.nbaApi.getAllPlayer()

        if (response.isSuccessful) {
            return@withContext response.body()?.data!! //TODO better error handling
        }

        return@withContext arrayListOf()
    }


    fun playersPagingSource(): PagingSource<Int, Player> {
        return PlayersPagingSource(this)
    }


}