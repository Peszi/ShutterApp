package com.pheasant.shutterapp.shutter.api.request;

import android.util.Log;

import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-06-21.
 */

public class FriendsPhotosListRequest extends Request {

    private ArrayList<PhotoData> photosList;

    public FriendsPhotosListRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("images");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.GET);
    }

    @Override
    protected void onSuccess(Object result) {
        this.photosList = new ArrayList<>();
        try {
            JSONObject jsonResult = new JSONObject((String) result);
            if (!jsonResult.getBoolean("error")) {
                JSONArray imagesList = jsonResult.getJSONArray("images");
                for (int i = 0; i < imagesList.length(); i++) {
                    JSONObject json = (JSONObject) imagesList.get(i);
                    PhotoData photoData = new PhotoData();
                    photoData.setImageId(json.getInt("id"));
                    photoData.setCreatorId(json.getInt("creator_id"));
                    photoData.setCreatorName(json.getString("creator_name"));
                    photoData.setCreatedDate(json.getString("created_at"));
                    this.photosList.add(photoData);
                }
                this.getResultListener().onResult(Request.RESULT_OK);
            } else {
                this.getResultListener().onResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public ArrayList<PhotoData> getPhotosList() {
        return this.photosList;
    }
}
