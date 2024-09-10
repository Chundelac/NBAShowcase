package dev.venc.nbasample.repository.datamodel

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("id") val teamId: Int,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val abbreviation: String,
    val conference: String,
    val division: String,
    val city: String
)