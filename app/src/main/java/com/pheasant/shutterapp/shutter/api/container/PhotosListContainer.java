package com.pheasant.shutterapp.shutter.api.container;

import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.shutter.api.listeners.PhotosListListener;
import com.pheasant.shutterapp.shutter.api.request.FriendsPhotosListRequest;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotosListContainer implements RequestResultListener {

    private ArrayList<PhotoData> photosList;

    private FriendsPhotosListRequest listRequest;
    private ArrayList<PhotosListListener> listListener;

    public PhotosListContainer(String apiKey) {
        this.photosList = new ArrayList<>();
        this.listListener = new ArrayList<>();
        this.listRequest = new FriendsPhotosListRequest(apiKey);
        this.listRequest.setOnRequestResultListener(this);
    }

    public void registerFriendsListener(PhotosListListener listListener) {
        this.listListener.add(listListener);
    }

    public void downloadPhotosList() {
        this.listRequest.cancel();
        this.listRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            final int changesCount = this.listRequest.getPhotosList().size() - this.photosList.size();
            this.photosList.clear();
            this.photosList.addAll(this.listRequest.getPhotosList());
            this.notifyListeners(changesCount);
        }
    }

    private void notifyListeners(int changesCount) {
        for (PhotosListListener listListener : this.listListener)
            listListener.onFriendsPhotosListDownloaded(changesCount);
    }

    public ArrayList<PhotoData> getPhotosList() {
        return this.photosList;
    }

}
