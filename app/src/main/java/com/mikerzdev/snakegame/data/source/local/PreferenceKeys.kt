package com.mikerzdev.snakegame.data.source.local

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val SCORE_KEY = stringPreferencesKey("scoreKey")
    val GAME_SETTINGS_KEY = stringPreferencesKey("gameSettingsKey")
}