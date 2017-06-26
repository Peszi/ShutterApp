package com.pheasant.shutterapp.network.download.user;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * Created by Peszi on 2017-05-24.
 */

public class UserDataManager implements UserPhotosListDownloader.DownloadListener, UserPhotosRequester {

    private UserData userData;
    private UserPhotosListDownloader photosListDownloader;
    private UserPhotosDownloader photosDownloader;

    private UserDataListener dataListener;

    public UserDataManager(UserData userData) {
        this.userData = userData;
        this.photosListDownloader = new UserPhotosListDownloader(userData.getApiKey());
        this.photosListDownloader.setDownloadListener(this);
        this.photosListDownloader.download();
        this.photosDownloader = new UserPhotosDownloader(userData.getApiKey());
    }

    public void setUserDataListener(UserDataListener dataListener) {
        this.dataListener = dataListener;
    }

    public UserData getUserData() {
        return this.userData;
    }

    @Override
    public void onListDownloaded(List<UserPhoto> userPhotos) {
        this.dataListener.onListLoaded(userPhotos);
        for (UserPhoto userPhoto : userPhotos)
            this.photosDownloader.addToDownload(userPhoto);
    }

    @Override
    public void reloadPhotosList() {
        this.photosListDownloader.download();
    }

    @Override
    public void reloadPhotos() {

    }
}
