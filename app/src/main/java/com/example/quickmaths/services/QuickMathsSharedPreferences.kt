package com.example.quickmaths.services

import android.content.Context
import android.content.SharedPreferences

class QuickMathsSharedPreferences(context: Context) {

    private val sharedFile = "sharedPrefFile"
    private val APP_PREF_INT_EXAMPLE = "intExamplePref"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(sharedFile, Context.MODE_PRIVATE)

    var intExamplePref: Int
        get() = preferences.getInt(APP_PREF_INT_EXAMPLE, -1)
        set(value) = preferences.edit().putInt(APP_PREF_INT_EXAMPLE, value).apply()
}