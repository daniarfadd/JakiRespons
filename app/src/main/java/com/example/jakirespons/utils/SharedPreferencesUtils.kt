package com.example.jakirespons.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.jakirespons.R

private const val KEY_LONGITUDE = "key-longitude"
private const val KEY_LATITUDE = "key-latitude"

private fun Context.getAppSharedPreferences() : SharedPreferences =
    getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

private fun Context.setSharedDouble(key: String, value: Double) {
    getAppSharedPreferences().edit()
        .putLong(key, java.lang.Double.doubleToLongBits(value))
        .apply()
}

private fun Context.getSharedDouble(key: String, defValue: Double) : Double {
    return if (!getAppSharedPreferences().contains(key))
        defValue
    else
        java.lang.Double.longBitsToDouble(getAppSharedPreferences().getLong(key, 0))
}

fun Context.setLongitude(longitude: Double){
    setSharedDouble(KEY_LONGITUDE, longitude)
}

fun Context.getLongitude() : Double {
    return getSharedDouble(KEY_LONGITUDE, 0.0)
}

fun Context.setLatitude(latitude: Double){
    setSharedDouble(KEY_LATITUDE, latitude)
}

fun Context.getLatitude() : Double {
    return getSharedDouble(KEY_LATITUDE, 0.0)
}

