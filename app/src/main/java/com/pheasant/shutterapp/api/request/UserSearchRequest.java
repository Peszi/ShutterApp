package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.data.StrangerData;
import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-06-06.
 */

public class UserSearchRequest extends BaseRequest {

    private ArrayList<StrangerData> strangersList;

    public UserSearchRequest(String apiKey) {
        super(RequestMethod.GET);
        this.getProperties().setAuthorizationKey(apiKey);
    }

    public void setKeyword(String keyword) {
        this.getProperties().setAddress("search/" + keyword);
        this.sendRequest();
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        this.strangersList = new ArrayList<>();
        try {
            if (!jsonResult.getBoolean("error")) {
                JSONArray imagesList = jsonResult.getJSONArray("users");
                for (int i = 0; i < imagesList.length(); i++) {
                    final JSONObject userObject = (JSONObject) imagesList.get(i);
                    StrangerData data = new StrangerData();
                    data.setId(userObject.getInt("id"));
                    data.setName(userObject.getString("name"));
                    data.setInvite(userObject.getInt("invite"));
//                    friendData.setAvatar(userObject.getInt("color"));
                    if (data.getInvite() < 2)
                        this.strangersList.add(data);
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

    public ArrayList<StrangerData> getStrangersList() {
        return this.strangersList;
    }
}