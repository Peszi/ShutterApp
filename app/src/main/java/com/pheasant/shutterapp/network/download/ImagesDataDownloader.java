package com.pheasant.shutterapp.network.download;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;

import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.network.request.photos.PhotosListRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public class ImagesDataDownloader implements RequestResultListener, Runnable {

    private List<PhotoData> photoDataList;
    private PhotosListRequest photosListRequest;
    private OnImageListDownloadListener onResultListener;
    private Context context;

    public ImagesDataDownloader(Context context, String apiKey) {
        this.photoDataList = new ArrayList<>();
        this.context = context;
        this.photosListRequest = new PhotosListRequest(apiKey);
        this.photosListRequest.setOnRequestResultListener(this);
    }

    public void setOnImageListDownload(OnImageListDownloadListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void download() {
        this.photosListRequest.execute();
    }

    protected void completeData(List<UserData> userDataList) {
//        for (PhotoData photoData : this.photoDataList)
//            for (UserData userData : userDataList)
//                if (photoData.getCreatorId() == userData.getId())
//                    photoData.setUserData(userData);
    }

    private boolean addIfNotExist(PhotoData photoData) {
        for (PhotoData imgData : this.photoDataList)
            if (imgData.getImageId() == photoData.getImageId())
                return false;
        this.photoDataList.add(photoData);
        return true;
    }

    @Override
    public void run() {
        this.download();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            List<PhotoData> newPhotosList = this.completeWith(this.photosListRequest.getImagesData());
            if (this.onResultListener != null && newPhotosList.size() > 0)
                this.onResultListener.onNewImagesDownloaded(newPhotosList);
        } else {
            Snackbar.make(((Activity) this.context).getWindow().getDecorView(), "server connection error", Snackbar.LENGTH_LONG).show();
            new Handler().postDelayed(this, UserDataDownloader.RETRY_INTERVAL);
        }
    }

    private List<PhotoData> completeWith(List<PhotoData> imagesList) {
        List<PhotoData> newPhotosList = new ArrayList<>();
        if (imagesList != null)
            for (PhotoData imgData : imagesList)
                if (this.addIfNotExist(imgData))
                    newPhotosList.add(imgData);
        return newPhotosList;
    }

    protected PhotoData getPhotoDataByPhoto(int id) {
        for (PhotoData photoData : this.photoDataList)
            if (photoData.getImageId() == id)
                return photoData;
        return null;
    }

    protected List<PhotoData> getPhotosList() {
        return this.photoDataList;
    }

    public interface OnImageListDownloadListener {
        void onNewImagesDownloaded(List<PhotoData> imagesList);
    }
}
