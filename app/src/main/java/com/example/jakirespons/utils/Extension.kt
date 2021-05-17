package com.example.jakirespons.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(@StringRes res: Int, @IdRes anchor: Int) {
    Snackbar.make(this, resources.getString(res), Snackbar.LENGTH_SHORT)
        .setAnchorView(anchor)
        .show()
}

fun View.showSnackbar(@StringRes res: Int) {
    Snackbar.make(this, resources.getString(res), Snackbar.LENGTH_SHORT)
        .show()
}

fun Context.showToast(@StringRes res: Int, duration: Int) {
    Toast.makeText(this, res, duration).show()
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