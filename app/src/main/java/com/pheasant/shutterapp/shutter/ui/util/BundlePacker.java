package com.pheasant.shutterapp.shutter.ui.util;

import android.content.Intent;
import android.os.Bundle;

import com.pheasant.shutterapp.network.download.user.UserData;
import com.pheasant.shutterapp.utils.IntentKey;

/**
 * Created by Peszi on 2017-11-06.
 */

public class BundlePacker {

    public static Bundle prepareBundle(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.USER_API_KEY, ((UserData) intent.getSerializableExtra(IntentKey.USER_DATA)).getApiKey());
        return bundle;
    }
}
