package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.shutter.api.util.ServerMessage;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

/**
 * Created by Peszi on 2017-11-06.
 */

public class InternetUtility {

    // TODO check internet connection
    public static boolean isInternetConnection(StatusProvider statusProvider) {
        if (true) {
            return true;
        } else {
            statusProvider.notifyError(ServerMessage.NO_INTERNET);
        }
        return false;
    }
}
