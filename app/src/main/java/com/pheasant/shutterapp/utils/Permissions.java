package com.pheasant.shutterapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Peszi on 2017-05-07.
 */

public class Permissions {

    public static final String CAMERA_PREMISSION = Manifest.permission.CAMERA;
    public static final int CAMERA_PERMISSION_CODE = 0;
    public static final String LOCATION_PREMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int LOCATION_PERMISSION_CODE = 1;

    public static boolean havePermission(Activity activity, String permissionName) {
        return ContextCompat.checkSelfPermission(activity, permissionName) == PackageManager.PERMISSION_GRANTED ? true : false;
    }

    public static void requestPermission(Activity activity, String permissionName, int permissionCode) {
        if (!Permissions.havePermission(activity, permissionName))
            ActivityCompat.requestPermissions(activity, new String[] {permissionName}, permissionCode);
    }
}
