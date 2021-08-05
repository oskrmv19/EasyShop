package com.oskr19.easyshop.core.data.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by oscar.vergara on 25/07/2021
 */
@Suppress("UNCHECKED_CAST")
class EasyShopPrefs(private val context: Context) {
    companion object {
        private const val PREF = "EasyShopPref"
        private const val PREF_SITE_ID = "site_id"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPref.edit().run {
            remove(PREF_SITE_ID)
        }.apply()
    }

    fun saveSiteID(token: String){
        put(PREF_SITE_ID, token)
    }

    fun getSiteID() : String {
        return get(PREF_SITE_ID, String::class.java)
    }
}