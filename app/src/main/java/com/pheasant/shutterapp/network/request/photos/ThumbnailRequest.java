package com.pheasant.shutterapp.network.request.photos;

import android.graphics.Bitmap;
import android.util.Log;

import com.pheasant.shutterapp.network.download.user.UserPhoto;
import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.BitmapRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

/**
 * Created by Peszi on 2017-05-24.
 */

public class ThumbnailRequest extends Request {

    private Bitmap photoData;

    public ThumbnailRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_BITMAP);
        this.setAddress("thumbnail");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.GET);
    }

    public void setId(int id) {
        this.setArgument(String.valueOf(id));
    }

    @Override
    protected void onSuccess(Object result) {
        this.photoData = (Bitmap) result;
        if (this.photoData != null)
            this.getResultListener().onResult(Request.RESULT_OK);
        else
            this.getResultListener().onResult(Request.RESULT_ERR);
    }

    public Bitmap getPhotoData() {
        return this.photoData;
    }
}
