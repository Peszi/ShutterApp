package com.pheasant.shutterapp.api.request;

import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-04-22.
 */

public class RegisterRequest extends BaseRequest {

    private boolean isSuccess;
    private String serverMessage;

    public RegisterRequest() {
        super(RequestMethod.POST);
        this.getProperties().setAddress("register");
    }

    public void sendRequest(String name, String email, String password, int avatar) {
        this.getProperties().clearParameters();
        this.getProperties().addParameter("name", name);
        this.getProperties().addParameter("email", email);
        this.getProperties().addParameter("password", password);
        this.getProperties().addParameter("color", String.valueOf(avatar));
        this.sendRequest();
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        try {
            this.isSuccess = !jsonResult.getBoolean("error");
            this.serverMessage = jsonResult.getString("message");
            if (this.isSuccess) {
                this.resultListener.onRequestResult(Request.RESULT_OK);
            } else {
                this.resultListener.onRequestResult(Request.RESULT_ERR);
            }
        } catch (JSONException e) {
            this.resultListener.onRequestResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public boolean isSuccess() { return this.isSuccess; }

    public String getServerMessage() { return this.serverMessage; }

}
