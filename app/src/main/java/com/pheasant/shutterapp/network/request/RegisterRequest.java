package com.pheasant.shutterapp.network.request;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-04-22.
 */

public class RegisterRequest extends Request {

    private String message;

    public RegisterRequest(final String name, final String email, final String password, final int avatar) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("register");
        this.setMethod(RequestMethod.POST);
        this.addParameter("name", name);
        this.addParameter("email", email);
        this.addParameter("password", password);
        this.addParameter("color", String.valueOf(avatar));
    }

    @Override
    protected void onSuccess(Object result) {
        try {
            JSONObject jsonResult = new JSONObject((String) result);
            if (!jsonResult.getBoolean("error")) {
                this.message = jsonResult.getString("message");
                this.getResultListener().onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return this.message;
    }
}
