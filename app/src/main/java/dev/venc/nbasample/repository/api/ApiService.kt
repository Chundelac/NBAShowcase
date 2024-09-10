package dev.venc.nbasample.repository.api

import dev.venc.nbasample.repository.datamodel.AllPlayersResponse
import dev.venc.nbasample.repository.datamodel.PlayerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/v1/players/{id}")
    suspend fun getPlayer(@Path("id") id: Int): Response<PlayerResponse>


    @GET("/v1/players")
    suspend fun getAllPlayer(): Response<AllPlayersResponse>
}