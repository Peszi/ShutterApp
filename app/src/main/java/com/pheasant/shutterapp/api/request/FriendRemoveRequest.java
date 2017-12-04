package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-06-17.
 */

public class FriendRemoveRequest extends BaseRequest {

    private int friendId;

    public FriendRemoveRequest(String apiKey) {
        super(RequestMethod.DELETE);
        this.getProperties().setAuthorizationKey(apiKey);
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
        this.getProperties().setAddress("friends/" + String.valueOf(this.friendId));
        this.sendRequest();
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        try {
            if (!jsonResult.getBoolean("error")) {
                this.resultListener.onResult(Request.RESULT_OK);
            } else {
                this.resultListener.onResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.resultListener.onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public int getFriendId() {
        return this.friendId;
    }
}