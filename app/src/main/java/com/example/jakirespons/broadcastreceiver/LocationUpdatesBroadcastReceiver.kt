package com.example.jakirespons.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.jakirespons.BuildConfig
import com.example.jakirespons.utils.setLatitude
import com.example.jakirespons.utils.setLongitude
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationResult


/**
 * Receiver for handling location updates.
 *
 * For apps targeting API level O and above
 * {@link android.app.PendingIntent#getBroadcast(Context, int, Intent, int)} should be used when
 * requesting location updates in the background. Due to limits on background services,
 * {@link android.app.PendingIntent#getService(Context, int, Intent, int)} should NOT be used.
 *
 *  Note: Apps running on "O" devices (regardless of targetSdkVersion) may receive updates
 *  less frequently than the interval specified in the
 *  {@link com.google.android.gms.location.LocationRequest} when the app is no longer in the
 *  foreground.
 */
class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive() context:$context, intent:$intent")

        if (intent.action == ACTION_PROCESS_UPDATES) {

            // Checks for location availability changes.
            LocationAvailability.extractLocationAvailability(intent).let { locationAvailability ->
                if (!locationAvailability.isLocationAvailable) {
                    Log.d(TAG, "Location services are no longer available!")
                }
                Log.d(TAG, "Location services are still available!")
            }

            LocationResult.extractResult(intent).let { locationResult ->
                var lat = 0.0
                var longi = 0.0
                locationResult.locations.map { location ->
                    lat = location.latitude
                    longi = location.longitude
                }

                Log.d(TAG, "lat = $lat")
                Log.d(TAG, "long = $longi")

                if (lat != 0.0 && longi != 0.0){
                    context.setLatitude(lat)
                    context.setLongitude(longi)
                }
            }
        }
    }

    companion object {
        const val ACTION_PROCESS_UPDATES =
            BuildConfig.APPLICATION_ID+".action." +
                    "PROCESS_UPDATES"

        private const val TAG = "LUBroadcastReceiver"

    }
}
