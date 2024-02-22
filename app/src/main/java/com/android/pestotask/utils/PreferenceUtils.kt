package com.android.pestotask.utils

import android.content.Context
import android.content.SharedPreferences

object  PreferenceUtils {

    private const val PREF_NAME = "TaskSharedPreference"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun putBoolean(context: Context,  key: String, value: Boolean) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply()
    }

    fun putString(context: Context,  key: String, value: String) {
        getSharedPreferences(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String): String? {
        return getSharedPreferences(context).getString(key, "")
    }

    fun clearData(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }


}