package com.pheasant.shutterapp.network.download.user;

import com.pheasant.shutterapp.network.request.util.OnRequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;

import java.util.List;

/**
 * Created by Peszi on 2017-05-24.
 */

public class UserPhotosListDownloader implements OnRequestResultListener {

    private List<UserPhoto> photoDataList;
    private UserPhotosListRequest photosRequest;

    private DownloadListener downloadListener;

    private String apiKey;

    public UserPhotosListDownloader(String apiKey) {
        this.apiKey = apiKey;
        this.photosRequest = new UserPhotosListRequest(apiKey);
        this.photosRequest.setOnRequestResultListener(this);
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void download() {
        this.photosRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            if (this.downloadListener != null && this.photosRequest.getUserPhotos().size() > 0) {
                this.downloadListener.onListDownloaded(this.photosRequest.getUserPhotos());
            }
        } else {

        }
    }

    public interface DownloadListener {
        void onListDownloaded(List<UserPhoto> userPhotos);
    }
}
