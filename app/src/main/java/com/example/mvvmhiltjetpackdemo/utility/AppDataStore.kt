package com.example.mvvmhiltjetpackdemo.utility

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object AppDataStore {

    private val Context.dataStore by preferencesDataStore("app_prefs")


    suspend fun putString(context: Context, key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { prefs ->
            prefs[prefKey] = value
        }
    }

    fun getString(context: Context, key: String, default: String = ""): Flow<String> {
        val prefKey = stringPreferencesKey(key)
        return context.dataStore.data.map { prefs ->
            prefs[prefKey] ?: default
        }
    }


    suspend fun putBoolean(context: Context, key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        context.dataStore.edit { prefs ->
            prefs[prefKey] = value
        }
    }

    fun getBoolean(context: Context, key: String, default: Boolean = false): Flow<Boolean> {
        val prefKey = booleanPreferencesKey(key)
        return context.dataStore.data.map { prefs ->
            prefs[prefKey] ?: default
        }
    }

    suspend fun clear(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}