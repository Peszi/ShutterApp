package com.pheasant.shutterapp.shutter.api.friends;

import com.pheasant.shutterapp.shutter.api.data.StrangerData;
import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-06-06.
 */

public class UserSearchRequest extends Request {

    private ArrayList<StrangerData> strangersList;

    public UserSearchRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("search");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.GET);
    }

    public void setKeyword(String keyword) {
        this.setArgument(keyword);
    }

    @Override
    protected void onSuccess(Object result) {
        this.strangersList = new ArrayList<>();
        try {
            JSONObject jsonResult = new JSONObject((String) result);
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
                this.getResultListener().onResult(Request.RESULT_OK);
            } else {
                this.getResultListener().onResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public ArrayList<StrangerData> getStrangersList() {
        return this.strangersList;
    }
}