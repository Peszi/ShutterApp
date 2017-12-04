package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-12-04.
 */

public class PhotoRemoveRequest extends BaseRequest {

    private int photoId;

    public PhotoRemoveRequest(String apiKey) {
        super(RequestMethod.DELETE);
        this.getProperties().setAuthorizationKey(apiKey);
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
        this.getProperties().setAddress("images/" + String.valueOf(this.photoId));
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        try {
            if (!jsonResult.getBoolean("error")) {
                this.resultListener.onResult(Request.RESULT_OK);
            } else {
                this.resultListener.onResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.resultListener.onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public int getPhotoId() {
        return this.photoId;
    }
}