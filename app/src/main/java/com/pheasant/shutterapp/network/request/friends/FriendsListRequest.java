package com.pheasant.shutterapp.network.request.friends;

import android.util.Log;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-07.
 */

public class FriendsListRequest extends Request {

    private ArrayList<FriendData> friendsList;

    public FriendsListRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("friends");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.GET);
    }

    @Override
    protected void onSuccess(Object result) {
        Log.d("RESPONSE", "[friends success...]");
        this.friendsList = new ArrayList<>();
        try {
            JSONObject jsonResult = new JSONObject((String) result);
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
                if (this.getFriendsRequestListener() != null)
                    this.getFriendsRequestListener().onListUpdated(this.friendsList);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public List<FriendData> getFriendsList() {
        return this.friendsList;
    }

}
