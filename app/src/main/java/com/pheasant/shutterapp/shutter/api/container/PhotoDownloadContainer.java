package com.pheasant.shutterapp.shutter.api.container;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.pheasant.shutterapp.shutter.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.shutter.api.request.PhotoDownloadRequest;
import com.pheasant.shutterapp.shutter.api.requester.PhotoUploadRequest;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoDownloadContainer implements Runnable, Handler.Callback {

    private LinkedList<Integer> photoBuffer;

    private PhotoDownloadRequest downloadRequest;

    private Handler statusHandler;

    private PhotoDownloadListener downloadListener;

    public PhotoDownloadContainer(String apiKey) {
        this.photoBuffer = new LinkedList<>();
        this.downloadRequest = new PhotoDownloadRequest(apiKey);
        this.statusHandler = new Handler(this);
        new Thread(this).start();
    }

    public void setDownloadListener(PhotoDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void downloadPhoto(int photoId) {
        synchronized (this.photoBuffer) {
            this.photoBuffer.add(photoId);
            this.photoBuffer.notify();
        }
    }

    @Override
    public void run() {
        Integer photoId;
        while (true) {
            synchronized (this.photoBuffer) {
                if (this.photoBuffer.isEmpty()) {
                    try {
                        this.photoBuffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                photoId = this.photoBuffer.poll();
            }
            // TODO is exist
            this.downloadRequest.setData(photoId);
            final Bitmap photoBitmap = this.downloadRequest.download();
            this.notifyListeners(new PhotoDataHolder(photoId, photoBitmap));
        }
    }

    private void notifyListeners(PhotoDataHolder photoDataHolder) {
        Message message = new Message();
        message.obj = photoDataHolder;
        this.statusHandler.sendMessage(message);
    }

    @Override
    public boolean handleMessage(Message msg) {
        final PhotoDataHolder photoDataHolder = (PhotoDataHolder) msg.obj;
        if (this.downloadListener != null)
            this.downloadListener.onPhoto(photoDataHolder.getPhotoId(), photoDataHolder.getPhotoBitmap());
        return false;
    }

    public class PhotoDataHolder {

        private int photoId;
        private Bitmap photoBitmap;

        public PhotoDataHolder(int photoId, Bitmap photoBitmap) {
            this.photoId = photoId;
            this.photoBitmap = photoBitmap;
        }

        public int getPhotoId() {
            return this.photoId;
        }

        public Bitmap getPhotoBitmap() {
            return this.photoBitmap;
        }

    }
}
