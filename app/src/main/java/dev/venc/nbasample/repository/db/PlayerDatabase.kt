package dev.venc.nbasample.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.venc.nbasample.repository.datamodel.Player
import dev.venc.nbasample.repository.db.dao.PlayerDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@Database(entities = [Player::class], version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao

    companion object {
        private var _instance: Deferred<PlayerDatabase>? = null

        suspend fun getInstance(): PlayerDatabase =
            _instance?.await()!!

        suspend fun buildDatabase(context: Context) = withContext(Dispatchers.IO) {
            _instance?.await()
            val localInstance = _instance
            if (localInstance != null && localInstance.isCompleted) {
                return@withContext
            }
            _instance = async {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext, PlayerDatabase::class.java, "room_database"
                ).build()
                return@async newInstance
            }
        }
    }
}