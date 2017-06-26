package com.pheasant.shutterapp.network.download.user;

import android.util.Log;

import com.pheasant.shutterapp.network.request.photos.ThumbnailRequest;
import com.pheasant.shutterapp.network.request.util.OnRequestResultListener;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Peszi on 2017-05-24.
 */

public class UserPhotosDownloader implements OnRequestResultListener {

    private ThumbnailRequest thumbnailRequest;
    private Queue<UserPhoto> photosQueue = new LinkedList<>();
    private boolean isDownloading;

    public UserPhotosDownloader(String apiKey) {
        this.thumbnailRequest = new ThumbnailRequest(apiKey);
        this.thumbnailRequest.setOnRequestResultListener(this);
    }

    public void addToDownload(UserPhoto userPhoto) {
        this.photosQueue.add(userPhoto);
        if (!this.isDownloading)
            this.downloadNext();
    }

    public void downloadNext() {
        if (!this.photosQueue.isEmpty()) {
            this.isDownloading = true;
            this.photosQueue.peek().onLoading();
            this.thumbnailRequest.setId(this.photosQueue.peek().getImageId());
            this.thumbnailRequest.execute();
        }
    }

    @Override
    public void onResult(int resultCode) {
        this.isDownloading = false;
        if (resultCode < 0)
            Log.d("RESPONSE", "DOWNLOADING PHOTO ERR");
        UserPhoto userPhoto = this.photosQueue.poll();
        if (userPhoto != null)
            userPhoto.onLoaded(this.thumbnailRequest.getPhotoData());
        this.downloadNext();
    }
}
