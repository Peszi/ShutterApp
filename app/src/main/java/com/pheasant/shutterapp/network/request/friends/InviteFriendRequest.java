package com.pheasant.shutterapp.network.request.friends;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-06-17.
 */

public class InviteFriendRequest extends Request {

    public InviteFriendRequest(String apiKey, int friendId) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("invites");
        this.setArgument(String.valueOf(friendId));
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.POST);
    }

    public void setToPost() {
        this.setMethod(RequestMethod.POST);
    }

    public void setToDelete() {
        this.setMethod(RequestMethod.DELETE);
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
}