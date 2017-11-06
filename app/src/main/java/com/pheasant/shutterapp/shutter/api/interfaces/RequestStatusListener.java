package com.pheasant.shutterapp.shutter.api.interfaces;

/**
 * Created by Peszi on 2017-11-06.
 */

public interface RequestStatusListener {
    void onSuccess();
    void onError(String message);
}
