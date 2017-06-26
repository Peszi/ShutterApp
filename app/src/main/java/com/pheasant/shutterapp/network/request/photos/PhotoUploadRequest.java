package com.pheasant.shutterapp.network.request.photos;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-17.
 */

public class PhotoUploadRequest extends Request {

    public PhotoUploadRequest(Bitmap bitmap, List<Integer> recipients, String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("images");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.POST);
        for (Integer id : recipients)
            this.addParameter("user[]", "" + id);
        this.addParameter("image", this.getBase64Image(bitmap));
    }

    @Override
    protected void onSuccess(Object result) {
        try {
            JSONObject jsonResult = new JSONObject((String) result);
            if (!jsonResult.getBoolean("error")) {
                Log.d("RESPONSE", jsonResult.getString("message"));
                this.getResultListener().onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    private String getBase64Image(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        bitmap.recycle();
        String encodedImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        try {
            return URLEncoder.encode(encodedImage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}