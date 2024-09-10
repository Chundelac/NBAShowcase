package dev.venc.nbasample.repository.api

import dev.venc.nbasample.repository.datamodel.Player

object PlayerApiDataSource {
    suspend fun getPlayerFromApi(id: Int): Player {
        val apiResponse = NBARetrofitService.nbaApi.getPlayer(id)

        if (apiResponse.isSuccessful) {
            return apiResponse.body()?.data!!
        }
        throw (Exception("Player not found")) //TODO better error handling
    }

    suspend fun getPlayersFromApi(startingId: Int, amount: Int): List<Player> {
        val apiResponse = NBARetrofitService.nbaApi.getPlayers(startingId, amount)

        if (apiResponse.isSuccessful) {
            return apiResponse.body()?.data!! //TODO better error handling
        }

        return arrayListOf()
    }
}