package com.pheasant.shutterapp.network.request.friends;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;
import com.pheasant.shutterapp.shutter.api.util.InviteHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-06-17.
 */

public class InviteFriendRequest extends Request {

    private InviteHolder inviteHolder;

    public InviteFriendRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("invites");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.POST);
    }

    public void sendRequest(InviteHolder inviteHolder) {
        this.inviteHolder = inviteHolder;
        this.setArgument(String.valueOf(inviteHolder.getUserId()));
        if (inviteHolder.isToSend())
            this.setMethod(RequestMethod.POST);
        else
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

    public InviteHolder getInviteHolder() {
        return this.inviteHolder;
    }

}