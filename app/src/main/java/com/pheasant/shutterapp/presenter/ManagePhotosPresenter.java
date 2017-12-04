package com.pheasant.shutterapp.presenter;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.api.ShutterApiInterface;
import com.pheasant.shutterapp.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.api.listeners.PhotoRemoveListener;
import com.pheasant.shutterapp.api.listeners.PhotosListListener;
import com.pheasant.shutterapp.api.requester.PhotoRemoveRequester;
import com.pheasant.shutterapp.ui.features.browse.PhotoAdapter;
import com.pheasant.shutterapp.ui.interfaces.BrowsePhotosView;
import com.pheasant.shutterapp.ui.listeners.BrowsePhotosViewListener;

/**
 * Created by Peszi on 2017-11-30.
 */

public class ManagePhotosPresenter implements BrowsePhotosViewListener, PhotosListListener, PhotoDownloadListener, PhotoRemoveListener, PhotoAdapter.PhotoAdapterListener {

    private BrowsePhotosView browsePhotosView;

    private ShutterApiInterface shutterApiInterface;
    private PhotoRemoveRequester photoRemoveRequester;

    public void setShutterDataManager(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
        this.shutterApiInterface.registerFriendsPhotosListListener(this);
        this.shutterApiInterface.setPhotoDownloadListener(this);
        this.photoRemoveRequester = new PhotoRemoveRequester(shutterApiInterface.getApiKey());
        this.photoRemoveRequester.setListener(this);
    }

    public void setView(BrowsePhotosView browsePhotosView) {
        this.browsePhotosView = browsePhotosView;
    }

    @Override
    public void onPageShowEvent() {
        this.shutterApiInterface.downloadPhotos();
    }

    @Override
    public void onRefreshEvent() {
        this.shutterApiInterface.downloadPhotos();
    }

    // Photos List Callback

    @Override
    public void onFriendsPhotosListDownloaded(int changesCount) {
        this.browsePhotosView.setRefreshing(false);
        this.browsePhotosView.updatePhotosList(this.shutterApiInterface.getFriendsPhotos());
    }

    // Photo Adapter Listener

    @Override
    public void getThumbnail(int photoId) {
        this.shutterApiInterface.getThumbnail(photoId);
    }

    @Override
    public void getPhoto(int photoId) {
        this.shutterApiInterface.getPhoto(photoId);
    }

    @Override
    public void removePhoto(int photoId) {
        this.photoRemoveRequester.deletePhoto(photoId);
    }

    // Photo Get Callback

    @Override
    public void onPhoto(int photoId, Bitmap photoBitmap) {
        this.browsePhotosView.showPhotoDialog(photoBitmap);
    }

    @Override
    public void onThumbnail(int photoId, Bitmap photoBitmap) {
        this.browsePhotosView.updateThumbnail(photoId, photoBitmap);
    }

    // Photo Remove Callback

    @Override
    public void onPhotoRemove(int photoId) {
        this.shutterApiInterface.downloadPhotos();
    }
}
