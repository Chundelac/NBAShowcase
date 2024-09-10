package dev.venc.nbasample.repository.datamodel

data class AllPlayersResponse(
    val data: List<Player>,
    val meta: Meta
)