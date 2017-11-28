package com.pheasant.shutterapp.shutter.api.requester;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shutter.api.util.RequestMethod;
import com.pheasant.shutterapp.shutter.api.util.RequestProperties;
import com.pheasant.shutterapp.shutter.api.util.RequestUtility;

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

    public void upload() {
        final JSONObject response = RequestUtility.makeJSONRequest(this.requestProperties);
        //            if (!jsonResult.getBoolean("error")) {
//                JSONArray imagesList = jsonResult.getJSONArray("images");
//                for (int i = 0; i < imagesList.length(); i++) {
//                    final JSONObject imageObject = (JSONObject) imagesList.get(i);
//                    final PhotoData photoData = new PhotoData(imageObject.getInt("id"), imageObject.getInt("creator_id"), imageObject.getString("created_at"));
//                    this.imagesList.add(photoData);
//                }
//                this.getResultListener().onResult(Request.RESULT_OK);
//            }
//        } catch (JSONException e) {
//        this.getResultListener().onResult(Request.RESULT_ERR);
//            e.printStackTrace();
//        }
        Log.d("RESPONSE", "UPLOAD" + response);
    }
}
