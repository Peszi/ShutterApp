package com.pheasant.shutterapp.network.download.user;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-24.
 */

public class UserPhotosListRequest extends Request {

    private List<UserPhoto> userPhotos;

    public UserPhotosListRequest(final String apikey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("myphotos");
        this.setAuthorization(apikey);
        this.setMethod(RequestMethod.GET);
    }

    @Override
    protected void onSuccess(Object result) {
        this.userPhotos = new ArrayList<>();
        try {
            JSONObject jsonResult = new JSONObject((String) result);
            if (!jsonResult.getBoolean("error")) {
                JSONArray imagesList = jsonResult.getJSONArray("images");
                for (int i = imagesList.length()-1; i >= 0; i--) {
                    final JSONObject imageObject = (JSONObject) imagesList.get(i);
                    this.userPhotos.add(new UserPhoto(imageObject.getInt("id"), imageObject.getString("exist"), imageObject.getString("created_at")));
                }
                this.getResultListener().onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public List<UserPhoto> getUserPhotos() {
        return this.userPhotos;
    }
}
