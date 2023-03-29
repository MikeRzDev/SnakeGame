package com.mikerzdev.snakegame.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager
@Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    fun getData(key: Preferences.Key<String>) : Flow<String?> {
        return dataStore.data
            .map { preferences ->
                preferences[key]
            }
    }

    suspend fun setData(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }
}