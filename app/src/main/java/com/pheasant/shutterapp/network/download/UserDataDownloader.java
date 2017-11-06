package com.pheasant.shutterapp.network.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.pheasant.shutterapp.network.download.user.UserDataListener;
import com.pheasant.shutterapp.network.download.user.UserDataManager;
import com.pheasant.shutterapp.network.download.user.UserPhotosRequester;
import com.pheasant.shutterapp.network.request.photos.PhotoUploadRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.data.PhotoData;

import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public class UserDataDownloader implements ImagesDataDownloader.OnImageListDownloadListener, FriendsDataDownloader.OnFriendsListListener, PhotosDownloader.OnPhotoDownloadListener, Runnable {

    public static final int RELOAD_INTERVAL = 15000;
    public static final int RETRY_INTERVAL = 30000;

    private Handler intervalHandler;
    private FriendsDataDownloader friendsDataDownloader;
    private ImagesDataDownloader imagesDataDownloader;
    private PhotosDownloader photosDownloader;
    private OnFriendsPhotosListener onFriendsPhotosListener;

    // New one

    private UserDataManager userDataManager;

    public UserDataDownloader(Context context, com.pheasant.shutterapp.network.download.user.UserData userData) {
        this.setupDownloader();
        this.userDataManager = new UserDataManager(userData);
        this.friendsDataDownloader = new FriendsDataDownloader(context, userData.getApiKey());
        this.friendsDataDownloader.setOnFriendsListListener(this);
        this.imagesDataDownloader = new ImagesDataDownloader(context, userData.getApiKey());
        this.imagesDataDownloader.setOnImageListDownload(this);
        this.photosDownloader = new PhotosDownloader(context, userData.getApiKey());
        this.photosDownloader.setOnPhotoDownloadListener(this);
    }

    private void setupDownloader() {
        this.intervalHandler = new Handler();
        //this.intervalHandler.postDelayed(this, RELOAD_INTERVAL);
    }

    public void setOnFriendsPhotosListener(OnFriendsPhotosListener onFriendsPhotosListener) {
        this.onFriendsPhotosListener = onFriendsPhotosListener;
    }

    public void setUserDataListener(UserDataListener userDataListener) {
        this.userDataManager.setUserDataListener(userDataListener);
    }

    public UserPhotosRequester getUserPhotosRequester() {
        return this.userDataManager;
    }

    public void download() {
        this.friendsDataDownloader.download();
    }

    @Override
    public void onFriendsDownloaded(List<UserData> userDataList) {
        this.imagesDataDownloader.download();
    }

    @Override
    public void onNewImagesDownloaded(List<PhotoData> newPhotosList) {
        this.imagesDataDownloader.completeData(this.friendsDataDownloader.getUserDataList());
        this.onFriendsPhotosListener.onPhotosListPrepared(newPhotosList);
        this.photosDownloader.downloadImages(newPhotosList);
        this.photosDownloader.clearImagesFolder(this.imagesDataDownloader.getPhotosList());
    }

    @Override
    public void onPhotoDownloaded(int photoId, boolean downloaded) {
        if (downloaded)
            this.showNewPhotoMessage(photoId);
        this.onFriendsPhotosListener.onPhotoPrepared(this.photosDownloader.getThumbnail(photoId), photoId);
    }

    @Override
    public void run() {
        this.download();
        this.intervalHandler.postDelayed(this, RELOAD_INTERVAL);
    }

    private void showNewPhotoMessage(int photoId) {
        String message = "new photo received from ";
        final int creatorId = this.imagesDataDownloader.getPhotoDataByPhoto(photoId).getCreatorId();
        final UserData userData = this.friendsDataDownloader.getFriendDataById(creatorId);
        if (userData != null)
            message += userData.getName();
        //Snackbar.make(((Activity) this.friendsDataDownloader.getContext()).getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }

    public void uploadPhoto(Bitmap bitmap, List<Integer> recipients) {
        PhotoUploadRequest uploadRequest = new PhotoUploadRequest(bitmap, recipients, this.userDataManager.getUserData().getApiKey());
        uploadRequest.setOnRequestResultListener(new RequestResultListener() {
            @Override
            public void onResult(int resultCode) {

            }
        });
        uploadRequest.execute();
    }

    public com.pheasant.shutterapp.network.download.user.UserData getUserData() {
        return this.userDataManager.getUserData();
    }

    public List<UserData> getFriends() {
        return this.friendsDataDownloader.getUserDataList();
    }
}
