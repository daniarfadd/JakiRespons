package com.example.jakirespons.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import com.example.jakirespons.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(@StringRes res: Int) {
    Snackbar.make(this, resources.getString(res), Snackbar.LENGTH_SHORT)
        .show()
}

fun View.showSnackbar(string: String) {
    Snackbar.make(this, string, Snackbar.LENGTH_SHORT)
        .show()
}

fun Context.showToast(@StringRes res: Int, duration: Int) {
    Toast.makeText(this, res, duration).show()
}

fun Context.showToast(string: String, duration: Int) {
    Toast.makeText(this, string, duration).show()
}

fun Context.isConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    var result = false
    if (activeNetwork != null) {
        result = activeNetwork.isConnectedOrConnecting
    }
    return result
}

fun Context.getLocationManager() : LocationManager =
    getSystemService(Context.LOCATION_SERVICE) as LocationManager

fun Context.isLocationEnabled(): Boolean {
    var gpsEnabled = false
    var networkEnabled = false
    try {
        gpsEnabled = getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    try {
        networkEnabled = getLocationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return !(!gpsEnabled || !networkEnabled)
}

fun Context.showAlertReqGPSOn(retry: () -> Unit) {
    AlertDialog.Builder(this)
        .setMessage(getString(R.string.request_gps_enabled))
        .setPositiveButton(R.string.retry) { paramDialogInterface, paramInt ->
            paramDialogInterface.dismiss()
            retry()
        }
        .show()
}
/**
 * Helper functions to simplify permission checks/requests.
 */
fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
        return true
    }

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}