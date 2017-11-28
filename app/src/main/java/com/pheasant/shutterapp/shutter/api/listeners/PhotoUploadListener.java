package com.pheasant.shutterapp.shutter.api.listeners;

/**
 * Created by Peszi on 2017-11-28.
 */

public interface PhotoUploadListener {
    void onPhotoUploaded();
    void onPhotoUploadingProgress(int progress);
}
