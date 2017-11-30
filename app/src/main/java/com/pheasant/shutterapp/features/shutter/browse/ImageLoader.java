package com.pheasant.shutterapp.features.shutter.browse;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.pheasant.shutterapp.network.download.FriendsPhotosDownloader;

/**
 * Created by Peszi on 27.06.2017.
 */

public class ImageLoader extends AsyncTask<Void, Integer, Bitmap> {

    private FriendsPhotosDownloader photosDownloader;
    private int imageId;

    private OnLoaderListener onLoaderListener;

    public ImageLoader(FriendsPhotosDownloader photosDownloader, int imageId) {
        this.photosDownloader = photosDownloader;
        this.imageId = imageId;
    }

    public void setOnLoaderListener(OnLoaderListener onLoaderListener) {
        this.onLoaderListener = onLoaderListener;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        if (photosDownloader.canGetImage(this.imageId))
            return photosDownloader.getPhotoThumbnail(this.imageId);
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (this.onLoaderListener != null) {
            if (result != null)
                this.onLoaderListener.onLoad(result, this.imageId);
            else
                this.onLoaderListener.onErr(this.imageId);
        }
    }

    public interface OnLoaderListener {
        void onLoad(Bitmap bitmap, int imageId);
        void onErr(int imageId);
    }
}
