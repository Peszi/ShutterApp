package com.pheasant.shutterapp.network.download;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.network.request.data.PhotoData;
import com.pheasant.shutterapp.network.request.util.PhotoRequest;
import com.pheasant.shutterapp.network.request.util.Request;

import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public class PhotosDownloader implements PhotoRequest.OnImageRequestResult {

    private final String apiKey;
    private PhotoFileManager photoFileManager;
    private OnPhotoDownloadListener onPhotoDownloadListener;

    public PhotosDownloader(Context context, String apiKey) {
        this.photoFileManager = new PhotoFileManager(context);
        this.apiKey = apiKey;
//        this.photoFileManager.clearAllPhotos();
    }

    public void setOnPhotoDownloadListener(OnPhotoDownloadListener onPhotoDownloadListener) {
        this.onPhotoDownloadListener = onPhotoDownloadListener;
    }

    public void downloadImages(List<PhotoData> imagesList) {
        for (int i = imagesList.size()-1; i >= 0; i--)
            this.loadOrDownloadImage(imagesList.get(i));
    }

    protected void clearImagesFolder(List<PhotoData> imagesList) {
        this.photoFileManager.cleanUpPhotos(imagesList);
    }

    protected void loadOrDownloadImage(PhotoData photoData) {
        if (this.photoFileManager.isPhotoExist(photoData.getImageId())) {
            if (!this.photoFileManager.isThumbnailExist(photoData.getImageId())) {
                final Bitmap photo = this.photoFileManager.loadPhoto(photoData.getImageId());
                this.photoFileManager.storeThumbnail(photoData.getImageId(), this.photoFileManager.prepareThumbnail(photo));
            }
            this.onPhotoDownloadListener.onPhotoDownloaded(photoData.getImageId(), false);
        } else {
//            PhotoRequest photoRequest = new PhotoRequest(this.photoFileManager, this.apiKey, photoData);
//            photoRequest.setOnImageRequestResult(this);
//            photoRequest.execute();
        }
    }

    @Override
    public void onResult(int resultCode, int photoId) {
        if (resultCode == Request.RESULT_OK && this.onPhotoDownloadListener != null)
            this.onPhotoDownloadListener.onPhotoDownloaded(photoId, true);
    }

    protected Bitmap getPhoto(int photoId) {
        return this.photoFileManager.loadPhoto(photoId);
    }

    protected Bitmap getThumbnail(int photoId) {
        return this.photoFileManager.loadThumbnail(photoId);
    }

    public interface OnPhotoDownloadListener {
        void onPhotoDownloaded(int photoId, boolean downloaded);
    }
}
