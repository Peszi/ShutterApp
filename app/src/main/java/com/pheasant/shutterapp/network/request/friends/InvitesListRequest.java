package com.pheasant.shutterapp.network.request.friends;

import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-06-09.
 */

public class InvitesListRequest extends Request {

    private ArrayList<UserData> invitesList;

    public InvitesListRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("invites");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.GET);
    }

    @Override
    protected void onSuccess(Object result) {
        this.invitesList = new ArrayList<>();
        try {
            JSONObject jsonResult = new JSONObject((String) result);
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
                this.getResultListener().onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public ArrayList<UserData> getInvitesList() {
        return this.invitesList;
    }
}
