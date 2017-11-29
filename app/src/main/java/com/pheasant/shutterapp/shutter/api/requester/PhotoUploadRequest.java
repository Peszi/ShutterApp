package com.pheasant.shutterapp.shutter.api.requester;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shutter.api.util.RequestMethod;
import com.pheasant.shutterapp.shutter.api.util.RequestProperties;
import com.pheasant.shutterapp.shutter.api.util.RequestUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Peszi on 2017-11-27.
 */

public class PhotoUploadRequest {

    private RequestProperties requestProperties;

    public PhotoUploadRequest(String apiKey) {
        this.requestProperties = new RequestProperties(RequestMethod.POST);
        this.requestProperties.setAddress("images");
        this.requestProperties.setAuthorizationKey(apiKey);
    }

    public void setData(String bitmap, List<Integer> recipientsList) {
        this.requestProperties.clearParameters();
        this.requestProperties.addParameter("image", bitmap);
        for (Integer userId : recipientsList)
            this.requestProperties.addParameter("user[]", String.valueOf(userId));
    }

    public boolean upload() {
        final JSONObject jsonObject = RequestUtility.makeJSONRequest(this.requestProperties);
        try {
            if (!jsonObject.getBoolean("error"))
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
