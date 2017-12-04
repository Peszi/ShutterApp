package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.data.FriendData;
import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-07.
 */

public class FriendsListRequest extends BaseRequest {

    private ArrayList<FriendData> friendsList;

    public FriendsListRequest(String apiKey) {
        super(RequestMethod.GET);
        this.getProperties().setAuthorizationKey(apiKey);
        this.getProperties().setAddress("friends");
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        this.friendsList = new ArrayList<>();
        try {
            if (!jsonResult.getBoolean("error")) {
                JSONArray imagesList = jsonResult.getJSONArray("friends");
                for (int i = 0; i < imagesList.length(); i++) {
                    final JSONObject json = (JSONObject) imagesList.get(i);
                    final FriendData userData = new FriendData();
                    userData.setId(json.getInt("id"));
                    userData.setName(json.getString("name"));
                    userData.setAvatar(json.getInt("color"));
                    userData.setLastActivity(json.getString("activity"));
                    this.friendsList.add(userData);
                }
                this.resultListener.onResult(Request.RESULT_OK);
            }
            this.resultListener.onResult(Request.RESULT_ERR);
        } catch (JSONException e) {
            this.resultListener.onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public List<FriendData> getFriendsList() {
        return this.friendsList;
    }

}
