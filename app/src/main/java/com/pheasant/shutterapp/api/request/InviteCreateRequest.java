package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.InviteDataHolder;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-06-17.
 */

public class InviteCreateRequest extends BaseRequest {

    private InviteDataHolder inviteDataHolder;

    public InviteCreateRequest(String apiKey) {
        super(RequestMethod.POST);
        this.getProperties().setAuthorizationKey(apiKey);
    }

    public void sendRequest(InviteDataHolder inviteDataHolder) {
        this.inviteDataHolder = inviteDataHolder;
        this.getProperties().setAddress("invites/" + String.valueOf(inviteDataHolder.getUserId()));
        if (inviteDataHolder.isNotRemoving())
            this.getProperties().setRequestMethod(RequestMethod.POST);
        else
            this.getProperties().setRequestMethod(RequestMethod.DELETE);
        this.sendRequest();
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        try {
            if (!jsonResult.getBoolean("error")) {
                this.resultListener.onRequestResult(Request.RESULT_OK);
            } else {
                this.resultListener.onRequestResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.resultListener.onRequestResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public InviteDataHolder getInviteDataHolder() {
        return this.inviteDataHolder;
    }

}