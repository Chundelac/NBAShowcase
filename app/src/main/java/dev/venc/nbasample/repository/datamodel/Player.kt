package dev.venc.nbasample.repository.datamodel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "players")
data class Player(
    @PrimaryKey @SerializedName("id") val playerId: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val position: String,
    val height: String,
    val weight: String,
    @SerializedName("jersey_number") val jerseyNumber: String,
    val college: String,
    val country: String,
    @SerializedName("draft_year") val draftYear: Int,
    @SerializedName("draft_round") val draftRound: Int,
    @SerializedName("draft_number") val draftNumber: Int,
    @Embedded val team: Team
)