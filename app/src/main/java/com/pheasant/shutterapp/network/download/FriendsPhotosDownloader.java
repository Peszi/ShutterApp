package com.pheasant.shutterapp.network.download;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.network.request.util.PhotoRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shutter.api.io.PhotoFileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-06-21.
 */

public class FriendsPhotosDownloader implements PhotoRequest.OnImageRequestResult {

    private PhotoFileManager photoFileManager;
    private OnDownloadListener onDownloadListener;

    private List<Integer> downloadList = new ArrayList<>();
    private boolean inDownloading;

    private String apiKey;

    public FriendsPhotosDownloader(Context context, String apiKey) {
        this.photoFileManager = new PhotoFileManager(context);
        this.apiKey = apiKey;
//        this.photoFileManager.clearAllPhotos();
        this.photoFileManager.showAllPhotos();
    }

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    public void clearImagesFolder(List<PhotoData> imagesList) {
        this.photoFileManager.cleanUpPhotos(imagesList);
    }

    public boolean canGetImage(int photoId) {
        if (!this.photoFileManager.isPhotoExist(photoId)) {
            this.addToDownload(photoId);
            return false;
        }
        return true;
    }

    public void forceGetImage(int photoId) {
        if (this.downloadList.contains(photoId)) {
            this.downloadList.remove(photoId);
        }
        this.downloadList.add(0, photoId);
        this.downloadRequest();
    }

    @Override
    public void onResult(int resultCode, int photoId) {
        this.inDownloading = false;
        this.downloadList.remove((Integer) photoId);
        if (resultCode == Request.RESULT_OK) {
            if (this.onDownloadListener != null)
                this.onDownloadListener.onDownloaded(photoId);
        } else {
            this.downloadList.add(0, photoId);
        }
        this.downloadRequest();
    }

    private void addToDownload(int photoId) {
        if (!this.downloadList.contains(photoId)) {
            this.downloadList.add(0, photoId);
            this.downloadRequest();
        }
    }

    public void downloadRequest() {
        if (!this.downloadList.isEmpty())
            this.downloadImage(this.downloadList.get(this.downloadList.size()-1));
    }

    private void downloadImage(int photoId) {
        if (!this.inDownloading) {
            this.inDownloading = true;
            PhotoRequest photoRequest = new PhotoRequest(this.photoFileManager, this.apiKey, photoId);
            photoRequest.setOnImageRequestResult(this);
            photoRequest.execute();
        }
    }

    public Bitmap getPhoto(int photoId) {
        return this.photoFileManager.loadPhoto(photoId);
    }

    public Bitmap getPhotoThumbnail(int photoId) {
//        Bitmap inputBitmap = this.photoFileManager.loadPhoto(photoId);
//        inputBitmap = Bitmap.createScaledBitmap(inputBitmap, inputBitmap.getWidth()/4, inputBitmap.getHeight()/4, false);
//        return Bitmap.createBitmap(inputBitmap, 0, inputBitmap.getHeight()/2 - inputBitmap.getWidth()/2, inputBitmap.getWidth(), inputBitmap.getWidth());
        return this.photoFileManager.loadThumbnail(photoId);
    }

    public interface OnDownloadListener {
        void onDownloaded(int photoId);
    }
}