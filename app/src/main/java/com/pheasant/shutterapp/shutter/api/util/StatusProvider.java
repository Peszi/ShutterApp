package com.pheasant.shutterapp.shutter.api.util;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class StatusProvider {

    private ArrayList<RequestStatusListener> statusListeners;

    public StatusProvider() {
        this.statusListeners = new ArrayList<>();
    }

    public void registerListener(RequestStatusListener listener) {
        this.statusListeners.add(listener);
    }

    public void unregisterListener(RequestStatusListener listener) {
        this.statusListeners.remove(listener);
    }

    public void notifySuccess() {
        for (RequestStatusListener statusListener : this.statusListeners)
            statusListener.onSuccess();
    }

    public void notifyError(String message) {
        for (RequestStatusListener statusListener : this.statusListeners)
            statusListener.onError(message);
    }
}
