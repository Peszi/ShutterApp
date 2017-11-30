package com.pheasant.shutterapp.shutter.api.request;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.util.RequestMethod;
import com.pheasant.shutterapp.shutter.api.util.RequestProperties;
import com.pheasant.shutterapp.shutter.api.util.RequestUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoDownloadRequest {

    private RequestProperties requestProperties;

    public PhotoDownloadRequest(String apiKey) {
        this.requestProperties = new RequestProperties(RequestMethod.GET);
        this.requestProperties.setAuthorizationKey(apiKey);
    }

    public void setData(int photoId) {
        this.requestProperties.setAddress("images/" + photoId);
    }

    public Bitmap download() {
        return RequestUtility.makeBitmapRequest(this.requestProperties);
    }
}
