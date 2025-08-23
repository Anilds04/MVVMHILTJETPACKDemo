package com.example.mvvmhiltjetpackdemo.utility

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

object AppDataStore {

    private val Context.dataStore by preferencesDataStore("app_prefs")
}