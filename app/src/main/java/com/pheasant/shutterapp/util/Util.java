package com.pheasant.shutterapp.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

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

    public static boolean isInternetConnection(Context context, View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        Snackbar.make(view, R.string.internet_connection_error_message, Snackbar.LENGTH_LONG).show();
        return false;
    }

}
