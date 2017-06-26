package com.pheasant.shutterapp.network.request;

import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;
import com.pheasant.shutterapp.network.download.user.UserData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-04-21.
 */

public class LoginRequest extends Request {

    private UserData userData;

    public LoginRequest(final String email, final String password) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("login");
        this.setMethod(RequestMethod.POST);
        this.addParameter("email", email);
        this.addParameter("password", password);
    }

    @Override
    protected void onSuccess(Object result) {
        try {
            JSONObject jsonResult = new JSONObject((String) result);
            if (!jsonResult.getBoolean("error")) {
                final int avatar = jsonResult.getInt("color");
                final String name = jsonResult.getString("name");
                final String email = jsonResult.getString("email");
                final String created = jsonResult.getString("createdAt");
                this.userData = new UserData(avatar, name, email, created);
                this.userData.setApiKey(jsonResult.getString("apikey"));
                this.getResultListener().onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public UserData getUserData() {
        return this.userData;
    }
}
