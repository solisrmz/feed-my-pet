package com.example.petfeeder.Shared

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "mcu"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }
    fun getValueString(KEY_NAME: String): Boolean? {
        if (sharedPref.getString(KEY_NAME, null)!=null){
            return true
        }else{
            return false
        }
    }
    fun getString(KEY_NAME: String):String? {
        return sharedPref.getString(KEY_NAME, null)
    }
}

