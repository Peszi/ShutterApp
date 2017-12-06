package com.pheasant.shutterapp.api.container;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.pheasant.shutterapp.api.io.PhotoFileManager;
import com.pheasant.shutterapp.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.api.request.PhotoDownloadRequest;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoManager implements Runnable, Handler.Callback {

    private LinkedList<PhotoDataHolder> photoBuffer;

    private PhotoDownloadRequest downloadRequest;
    private PhotoFileManager photoFileManager;

    private Handler statusHandler;
    private PhotoDownloadListener downloadListener;

    public PhotoManager(Context context, String apiKey) {
        this.photoBuffer = new LinkedList<>();
        this.downloadRequest = new PhotoDownloadRequest(apiKey);
        this.photoFileManager = new PhotoFileManager(context);
        this.photoFileManager.showAllPhotos();
        this.statusHandler = new Handler(this);
        new Thread(this).start();
    }

    public void setDownloadListener(PhotoDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void getThumbnail(int photoId) {
        this.makePhotoRequest(photoId, false);
    }

    public void getPhoto(int photoId) {
        this.makePhotoRequest(photoId, true);
    }

    private void makePhotoRequest(int photoId, boolean isPhoto) {
        synchronized (this.photoBuffer) {
            this.photoBuffer.add(new PhotoDataHolder(photoId, isPhoto));
            this.photoBuffer.notify();
        }
    }

    @Override
    public void run() {
        PhotoDataHolder requestHolder;
        while (true) {
            synchronized (this.photoBuffer) {
                if (this.photoBuffer.isEmpty()) {
                    try {
                        this.photoBuffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                requestHolder = this.photoBuffer.poll();
            }
            final Bitmap photoBitmap;
            if (this.photoFileManager.isPhotoExist(requestHolder.getPhotoId())) {
//                Log.d("RESPONSE", "[thread] loading photo.... ID:" + requestHolder.getPhotoId());
                photoBitmap = this.photoFileManager.loadPhoto(requestHolder.getPhotoId());
            } else {
//                Log.d("RESPONSE", "[thread] downloading photo.... ID:" + requestHolder.getPhotoId());
                this.downloadRequest.setData(requestHolder.getPhotoId());
                photoBitmap = this.downloadRequest.download();
//                Log.d("RESPONSE", "[thread] saving photo.... ID:" + requestHolder.getPhotoId());
                this.photoFileManager.storePhoto(requestHolder.getPhotoId(), photoBitmap);
            }
            if (!requestHolder.isPhoto() && photoBitmap != null) {
                final Bitmap thumbnailBitmap = Bitmap.createBitmap(photoBitmap, 0, photoBitmap.getHeight() / 2 - photoBitmap.getWidth() / 2, photoBitmap.getWidth(), photoBitmap.getWidth());
                photoBitmap.recycle();
                this.notifyListeners(new PhotoResultHolder(requestHolder, thumbnailBitmap));
            } else {
                this.notifyListeners(new PhotoResultHolder(requestHolder, photoBitmap));
            }
//            Log.d("RESPONSE", "[thread] PHOTO DONE .... ID:" + requestHolder.getPhotoId());
        }
    }

    private void notifyListeners(PhotoResultHolder photoResultHolder) {
        Message message = new Message();
        message.obj = photoResultHolder;
        this.statusHandler.sendMessage(message);
    }

    @Override
    public boolean handleMessage(Message msg) {
        final PhotoResultHolder photoResultHolder = (PhotoResultHolder) msg.obj;
//        Log.d("RESPONSE", "[ui] PHOTO done id " + photoResultHolder.getPhotoId());
        if (this.downloadListener != null) {
            if (photoResultHolder.isPhoto())
                this.downloadListener.onPhoto(photoResultHolder.getPhotoId(), photoResultHolder.getPhotoBitmap());
            else
                this.downloadListener.onThumbnail(photoResultHolder.getPhotoId(), photoResultHolder.getPhotoBitmap());
        }
        return false;
    }

    private class PhotoDataHolder {

        private int photoId;
        private boolean isPhoto;

        public PhotoDataHolder(int photoId, boolean isPhoto) {
            this.photoId = photoId;
            this.isPhoto = isPhoto;
        }

        public int getPhotoId() {
            return this.photoId;
        }

        public boolean isPhoto() {
            return this.isPhoto;
        }

    }

    private class PhotoResultHolder {

        private PhotoDataHolder dataHolder;
        private Bitmap photoBitmap;

        public PhotoResultHolder(PhotoDataHolder dataHolder, Bitmap photoBitmap) {
            this.dataHolder = dataHolder;
            this.photoBitmap = photoBitmap;
        }

        public int getPhotoId() {
            return this.dataHolder.getPhotoId();
        }

        public Bitmap getPhotoBitmap() {
            return this.photoBitmap;
        }

        public boolean isPhoto() {
            return this.dataHolder.isPhoto();
        }

    }
}
