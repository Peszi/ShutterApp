package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.util.RequestMethod;
import com.pheasant.shutterapp.api.util.RequestProperties;
import com.pheasant.shutterapp.api.util.RequestUtility;

import org.json.JSONException;
import org.json.JSONObject;

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
