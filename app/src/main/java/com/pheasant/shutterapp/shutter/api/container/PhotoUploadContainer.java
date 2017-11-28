package com.pheasant.shutterapp.shutter.api.container;

import android.graphics.Bitmap;
import android.util.Log;

import com.pheasant.shutterapp.shutter.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.shutter.api.requester.PhotoUploadRequest;
import com.pheasant.shutterapp.shutter.api.util.PhotoUtility;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Peszi on 2017-11-27.
 */

public class PhotoUploadContainer implements Runnable {

    private LinkedList<UploadPhotoData> photoBuffer;

    private PhotoUploadRequest uploadRequest;

    private PhotoUploadListener uploadListener;

    public PhotoUploadContainer(String apiKey) {
        this.photoBuffer = new LinkedList<>();
        this.uploadRequest = new PhotoUploadRequest(apiKey);
        Thread thread = new Thread(this);
        thread.start();
    }

    public void setUploadListener(PhotoUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    public void uploadPhoto(Bitmap bitmap, List<Integer> recipientsList) {
        synchronized (this.photoBuffer) {
            this.photoBuffer.add(new UploadPhotoData(bitmap, recipientsList));
            this.photoBuffer.notify();
        }
    }

    @Override
    public void run() {
        UploadPhotoData uploadPhotoData;
        while (true) {
            synchronized (this.photoBuffer) {
                if (this.photoBuffer.isEmpty()) {
                    try {
                        this.photoBuffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                uploadPhotoData = this.photoBuffer.poll();
            }
            this.uploadRequest.setData(uploadPhotoData.getUserPhotoData(), uploadPhotoData.getRecipientsList());
            this.uploadRequest.upload();
        }
    }

    public class UploadPhotoData {

        private Bitmap photoData;
        private List<Integer> recipientsList;

        public UploadPhotoData(Bitmap photoData, List<Integer> recipientsList) {
            this.photoData = photoData;
            this.recipientsList = recipientsList;
        }

        public String getUserPhotoData() {
            return PhotoUtility.getBase64Image(this.photoData);
        }

        public List<Integer> getRecipientsList() {
            return this.recipientsList;
        }
    }
}
