package com.pheasant.shutterapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Peszi on 2017-04-19.
 */

public class Util {

    /* FONT */
    public final static String FONT_PATH_LIGHT = "fonts/Raleway-Light.ttf";
    public final static String FONT_PATH_THIN = "fonts/Raleway-Thin.ttf";

    public static void setupFont(Context context, Window window) {
        ViewGroup group = (ViewGroup) window.getDecorView().findViewById(android.R.id.content);
        Util.setAllTextView(context.getAssets(), group, Util.FONT_PATH_LIGHT);
    }

    public static void setupFont(Context context, View view, String font) {
        if (view instanceof ViewGroup) {
            Util.setAllTextView(context.getAssets(), (ViewGroup) view, font);
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(getFont(context.getAssets(), font));
        }
    }

    public static void setupFont(AssetManager assetManager, TextView textView) {
        textView.setTypeface(getFont(assetManager, Util.FONT_PATH_LIGHT));
    }

    private static void setAllTextView(AssetManager assetManager, ViewGroup parent, final String font) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                Util.setAllTextView(assetManager, (ViewGroup) child, font);
            } else if (child instanceof TextView) {
                ((TextView) child).setTypeface(getFont(assetManager, font));
            }
        }
    }

    private static Typeface getFont(AssetManager assetManager, final String font) {
        return Typeface.createFromAsset(assetManager, font);
    }

    /* INPUT FOCUS */
    public static void removeInputDirectFocus(Window window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /* CHECK INTERNET */
    public static void checkSelfPermission(Activity activity, String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    public static boolean isInternetConnection(Context context, View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        Snackbar.make(view, R.string.internet_connection_error_message, Snackbar.LENGTH_LONG).show();
        return false;
    }

    @Nullable
    public static Location getLastKnownLoaction(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        if (isNetworkEnabled) {
            Location loc = manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            Log.d("RESPONSE", "ENABLED NETWORK PROVIDER " );
            return loc;
        } else {
            Log.d("RESPONSE", "NO NETWORK PROVIDER");
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            return  manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return null;
    }

    public static String getLocationName(Context context) {
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            Location location = Util.getLastKnownLoaction(context);
            List<Address> address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" / ");
            }

            return address.get(0).getAddressLine(1) + " " + address.get(0).getCountryCode(); //This is the complete address. address.get(0).getLocality() + " " +
        } catch (IOException e) {
        } catch (NullPointerException e) {}
        return "no location data";
    }
}
