package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.data.UserData;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;
import com.pheasant.shutterapp.api.util.BaseRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-06-09.
 */

public class InvitesListRequest extends BaseRequest {

    private ArrayList<UserData> invitesList;

    public InvitesListRequest(String apiKey) {
        super(RequestMethod.GET);
        this.getProperties().setAuthorizationKey(apiKey);
        this.getProperties().setAddress("invites");
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        this.invitesList = new ArrayList<>();
        try {
            if (!jsonResult.getBoolean("error")) {
                JSONArray imagesList = jsonResult.getJSONArray("invites");
                for (int i = 0; i < imagesList.length(); i++) {
                    final JSONObject userObject = (JSONObject) imagesList.get(i);
                    final UserData userData = new UserData();
                    userData.setId(userObject.getInt("id"));
                    userData.setName(userObject.getString("name"));
                    userData.setAvatar(userObject.getInt("color"));
                    this.invitesList.add(userData);
                }
                this.resultListener.onRequestResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.resultListener.onRequestResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public ArrayList<UserData> getInvitesList() {
        return this.invitesList;
    }
}
