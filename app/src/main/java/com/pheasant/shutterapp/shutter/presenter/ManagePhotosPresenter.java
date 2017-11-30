package com.pheasant.shutterapp.shutter.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.pheasant.shutterapp.shutter.api.ShutterDataManager;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotosListListener;
import com.pheasant.shutterapp.shutter.ui.features.browse.PhotoAdapter;
import com.pheasant.shutterapp.shutter.ui.interfaces.BrowsePhotosView;
import com.pheasant.shutterapp.shutter.ui.listeners.BrowsePhotosViewListener;

/**
 * Created by Peszi on 2017-11-30.
 */

public class ManagePhotosPresenter implements BrowsePhotosViewListener, PhotosListListener, PhotoDownloadListener, PhotoAdapter.PhotoAdapterListener {

    private ShutterDataManager shutterDataManager;
    private BrowsePhotosView browsePhotosView;

    public void setShutterDataManager(ShutterDataManager shutterDataManager) {
        this.shutterDataManager = shutterDataManager;
        this.shutterDataManager.registerFriendsPhotosListListener(this);
        this.shutterDataManager.setPhotoDownloadListener(this);
    }

    public void setView(BrowsePhotosView browsePhotosView) {
        this.browsePhotosView = browsePhotosView;
    }

    @Override
    public void onPageShowEvent() {
        this.shutterDataManager.downloadFriendsPhotos();
    }

    @Override
    public void onRefreshEvent() {
        this.shutterDataManager.downloadFriendsPhotos();
    }

    // Photos List Callback

    @Override
    public void onFriendsPhotosListDownloaded(int changesCount) {
        this.browsePhotosView.refreshSetRefreshing(false);
        this.browsePhotosView.updatePhotosList(this.shutterDataManager.getFriendsPhotos());
    }

    // Photo Adapter Listener

    @Override
    public void getPhoto(int photoId) {
        this.shutterDataManager.getPhoto(photoId);
    }

    // Photo Get Callback

    @Override
    public void onPhoto(int photoId, Bitmap photoBitmap) {
        this.browsePhotosView.updatePhotoBitmap(photoId, photoBitmap);
    }
}
