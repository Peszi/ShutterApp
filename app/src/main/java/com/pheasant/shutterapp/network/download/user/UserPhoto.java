package com.pheasant.shutterapp.network.download.user;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-05-24.
 */

public class UserPhoto {

    private int imageId;
    private String exist;
    private String createdTime;
    private Bitmap bitmap;

    private boolean photoLoading;

    private UserPhotoListener photoListener;

    public UserPhoto(int imageId, String exist, String createdTime) {
        this.imageId = imageId;
        this.exist = exist;
        this.createdTime = createdTime;
    }

    public void setPhotoListener(UserPhotoListener photoListener) {
        this.photoListener = photoListener;
    }

    public void update(UserPhoto userPhoto) {
        this.exist = userPhoto.getExistTime();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getDate() {
        return this.createdTime;
    }

    public String getExistTime() {
        return this.exist;
    }

    public void onLoading() {
        this.photoLoading = true;
        if (this.photoListener != null)
            this.photoListener.onLoading(this.imageId);
    }

    public void onLoaded(Bitmap bitmap) {
        this.photoLoading = false;
        this.bitmap = bitmap;
        if (this.photoListener != null)
            this.photoListener.onLoaded(this.imageId, this.bitmap);
    }

    public interface UserPhotoListener {
        void onLoading(int photoId);
        void onLoaded(int photoId, Bitmap bitmap);
    }

    public boolean isLoading() {
        return this.photoLoading;
    }
}
