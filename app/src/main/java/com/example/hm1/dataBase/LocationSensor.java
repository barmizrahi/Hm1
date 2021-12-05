package com.example.hm1.dataBase;

import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.example.hm1.callBacks.CallBack_location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationSensor {

    private static LocationSensor me;
    Context context;
    private final FusedLocationProviderClient fusedLocationClient;

    public static LocationSensor getMe() {
        return me;
    }

    private LocationSensor(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public static void locationSensorInit(Context context) {
        if (me == null) {
            me = new LocationSensor(context);
        }
    }


    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    public void getLocation(CallBack_location cb) {
        if (checkPermission()) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                if (location != null) {
                    cb.showLocation(location.getLatitude(), location.getLongitude());
                } else {
                    cb.showLocation(0.0D, 0.0D);
                }
            });
        } else {
            cb.showLocation(0.0D, 0.0D);
        }

    }
}

