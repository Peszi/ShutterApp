package com.pheasant.shutterapp.shutter.api.request;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-06-17.
 */

public class RemoveFriendRequest extends Request {

    private int friendId;

    public RemoveFriendRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("friends");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.DELETE);
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
        this.setArgument(String.valueOf(this.friendId));
    }

    @Override
    protected void onSuccess(Object result) {
        try {
            JSONObject jsonResult = new JSONObject((String) result);
            if (!jsonResult.getBoolean("error")) {
                this.getResultListener().onResult(Request.RESULT_OK);
            } else {
                this.getResultListener().onResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public int getFriendId() {
        return this.friendId;
    }
}