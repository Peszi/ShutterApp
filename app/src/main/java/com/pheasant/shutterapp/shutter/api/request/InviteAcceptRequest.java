package com.pheasant.shutterapp.shutter.api.request;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;
import com.pheasant.shutterapp.shutter.api.util.InviteDataHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-06-17.
 */

public class InviteAcceptRequest extends Request {

    private InviteDataHolder inviteDataHolder;

    public InviteAcceptRequest(String apiKey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("friends");
        this.setAuthorization(apiKey);
        this.setMethod(RequestMethod.POST);
    }

    public void sendRequest(InviteDataHolder inviteDataHolder) {
        this.inviteDataHolder = inviteDataHolder;
        this.setArgument(String.valueOf(inviteDataHolder.getUserId()));
        if (inviteDataHolder.isNotRemoving())
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

    public InviteDataHolder getInviteDataHolder() {
        return this.inviteDataHolder;
    }

}