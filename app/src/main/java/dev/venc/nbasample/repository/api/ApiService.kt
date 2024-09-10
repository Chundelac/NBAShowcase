package dev.venc.nbasample.repository.api

import dev.venc.nbasample.repository.datamodel.AllPlayersResponse
import dev.venc.nbasample.repository.datamodel.PlayerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/players/{id}")
    suspend fun getPlayer(@Path("id") id: Int): Response<PlayerResponse>

    @GET("/v1/players")
    suspend fun getPlayers(@Query("cursor") startId: Int, @Query("per_page") amount: Int): Response<AllPlayersResponse>

    @GET("/v1/players")
    suspend fun getAllPlayers(): Response<AllPlayersResponse>
}