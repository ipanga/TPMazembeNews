package com.tootiyesolutions.tpmazembe.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    // Static variables available in all classes
    companion object{

        // Variable which will store time we update de DB
        private const val PREF_TIME = "Pref time"

        private var prefs: SharedPreferences? = null

        // Singleton pattern to allow only one thread to access the database at time
        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK){
            instance ?: buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferencesHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveUpdateTime(time: Long){
        prefs?.edit(commit = true) {putLong(PREF_TIME, time)}
    }

    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)
}