package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.data.PhotoData;
import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-06-21.
 */

public class FriendsPhotosListRequest extends BaseRequest {

    private ArrayList<PhotoData> photosList;

    public FriendsPhotosListRequest(String apiKey) {
        super(RequestMethod.GET);
        this.getProperties().setAuthorizationKey(apiKey);
        this.getProperties().setAddress("images");
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        this.photosList = new ArrayList<>();
        try {
            if (!jsonResult.getBoolean("error")) {
                JSONArray imagesList = jsonResult.getJSONArray("images");
                for (int i = 0; i < imagesList.length(); i++) {
                    JSONObject json = (JSONObject) imagesList.get(i);
                    PhotoData photoData = new PhotoData();
                    photoData.setImageId(json.getInt("id"));
                    photoData.setCreatorId(json.getInt("creator_id"));
                    photoData.setCreatorName(json.getString("creator_name"));
                    if (json.getInt("is_me") > 0) { photoData.setMe(true); }
                    photoData.setCreatedTime(json.getString("created_at"));
                    this.photosList.add(photoData);
                }
                this.resultListener.onRequestResult(Request.RESULT_OK);
            } else {
                this.resultListener.onRequestResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.resultListener.onRequestResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public ArrayList<PhotoData> getPhotosList() {
        return this.photosList;
    }
}
