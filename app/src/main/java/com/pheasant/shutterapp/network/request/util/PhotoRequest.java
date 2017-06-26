package com.pheasant.shutterapp.network.request.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.pheasant.shutterapp.network.request.data.PhotoData;
import com.pheasant.shutterapp.network.download.PhotoFileManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Peszi on 2017-05-05.
 */

public class PhotoRequest extends BaseRequest {

    private final String IMAGES_ADDRESS = "images/";

    private PhotoFileManager photoFileManager;
    private OnImageRequestResult resultListener;

    private String authorizationKey;
    private int photoId;

    public PhotoRequest(PhotoFileManager photoFileManager, String apiKey, int photoId) {
        this.photoFileManager = photoFileManager;
        this.authorizationKey = apiKey;
        this.photoId = photoId;
    }

    public void setOnImageRequestResult(OnImageRequestResult resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Log.d("RESPONSE", "BITMAP download ID: " + this.photoId);
            URL requestUrl = new URL(this.SERVER_ADDRESS + this.IMAGES_ADDRESS + this.photoId);
            HttpURLConnection requestConnection = (HttpURLConnection) requestUrl.openConnection();
            requestConnection.setRequestMethod(RequestMethod.GET);
            requestConnection.setRequestProperty("Authorization", this.authorizationKey);
            requestConnection.setDoInput(true);
            requestConnection.connect();
            if (requestConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Bitmap bitmap = BitmapFactory.decodeStream(requestConnection.getInputStream());
                if (bitmap != null) {
                    this.photoFileManager.storePhoto(this.photoId, bitmap);
                    this.photoFileManager.storeThumbnail(this.photoId, this.photoFileManager.prepareThumbnail(bitmap));
                    bitmap.recycle();
                    return "";
                }
                Log.e("IMAGE", "BITMAP CORRUPTED ID: " + this.photoId);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(Object result) {
        if (this.resultListener != null)
            this.resultListener.onResult(result != null ? Request.RESULT_OK : Request.RESULT_ERR, this.photoId);
    }

    public interface OnImageRequestResult {
        void onResult(int resultCode, int imageId);
    }
}
