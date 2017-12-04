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

    private String message;

    public RegisterRequest(final String name, final String email, final String password, final int avatar) {
        super(RequestMethod.POST);
        this.getProperties().setAddress("register");
        this.getProperties().addParameter("name", name);
        this.getProperties().addParameter("email", email);
        this.getProperties().addParameter("password", password);
        this.getProperties().addParameter("color", String.valueOf(avatar));
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        try {
            if (!jsonResult.getBoolean("error")) {
                this.message = jsonResult.getString("message");
                this.resultListener.onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.resultListener.onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return this.message;
    }
}
