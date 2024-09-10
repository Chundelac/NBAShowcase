package dev.venc.nbasample.repository.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "appsettings"
private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

class AppSettings(context: Context) {
    private val dataStore = context.dataStore

    private val _userHasSeenIntroDialog = booleanPreferencesKey("userHasSeenIntroDialog")
    val userHasSeenIntroDialog: Flow<Boolean> = context.dataStore.data.map {
        it[_userHasSeenIntroDialog] ?: false
    }
    suspend fun userHasSeenIntroDialog(value: Boolean) = dataStore.edit { it[_userHasSeenIntroDialog] = value }
}