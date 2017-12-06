package com.pheasant.shutterapp.util;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Peszi on 2017-11-06.
 */

public class BundlePacker {

    public static Bundle prepareApiKeyBundle(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.USER_API_KEY, intent.getStringExtra(IntentKey.USER_API_KEY));
        return bundle;
    }

    public static String getEmailFromBundle(Intent intent) {
        final Bundle bundle = intent.getExtras();
        return bundle.getString(IntentKey.USER_EMAIL);
    }

    public static String getPassFromBundle(Intent intent) {
        final Bundle bundle = intent.getExtras();
        return bundle.getString(IntentKey.USER_PASSWORD);
    }

}
