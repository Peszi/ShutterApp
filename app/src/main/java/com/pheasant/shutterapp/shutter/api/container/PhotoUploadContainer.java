package com.pheasant.shutterapp.shutter.api.container;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.pheasant.shutterapp.shutter.api.data.UploadPhotoData;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.shutter.api.requester.PhotoUploadRequest;
import com.pheasant.shutterapp.shutter.api.util.PhotoUtility;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Peszi on 2017-11-27.
 */

public class PhotoUploadContainer implements Runnable, Handler.Callback {

    private LinkedList<UploadPhotoData> photoBuffer;
    private boolean isSuccess;

    private PhotoUploadRequest uploadRequest;

    private Handler statusHander;
    private PhotoUploadListener uploadListener;

    public PhotoUploadContainer(String apiKey) {
        this.photoBuffer = new LinkedList<>();
        this.uploadRequest = new PhotoUploadRequest(apiKey);
        this.statusHander = new Handler(this);
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

    public void reUpload() {
        synchronized (this.photoBuffer) {
            this.photoBuffer.notify();
        }
    }

    @Override
    public void run() {
        UploadPhotoData uploadPhotoData;
        boolean isFailed = false;
        while (true) {
            synchronized (this.photoBuffer) {
                if (this.photoBuffer.isEmpty() || isFailed) {
                    try {
                        this.photoBuffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                uploadPhotoData = this.photoBuffer.poll();
            }
            this.uploadRequest.setData(uploadPhotoData.getUserPhotoData(), uploadPhotoData.getRecipientsList());
            isFailed = !this.uploadRequest.upload();
            if (isFailed) {
                synchronized (this.photoBuffer) {
                    this.photoBuffer.add(uploadPhotoData);
                }
            }
            this.notifyListeners(!isFailed);
        }
    }

    private void notifyListeners(boolean success) {
        this.isSuccess = success; // TODO should not work sometimes
        this.statusHander.sendEmptyMessage(0);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (this.uploadListener != null)
            this.uploadListener.onPhotoUploadStatusChange(this.isSuccess);
        return false;
    }
}
