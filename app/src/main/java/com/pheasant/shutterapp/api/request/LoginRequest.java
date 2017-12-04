package com.pheasant.shutterapp.api.request;

import android.util.Log;

import com.pheasant.shutterapp.api.util.BaseRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.api.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Peszi on 2017-04-21.
 */

public class LoginRequest extends BaseRequest {

    private String apiKey;

    public LoginRequest() {
        super(RequestMethod.POST);
        this.getProperties().setAddress("login");
    }

    public void sendRequest(String email, String password) {
        this.getProperties().clearParameters();
        this.getProperties().addParameter("email", email);
        this.getProperties().addParameter("password", password);
        this.sendRequest();
    }

    @Override
    public void onSuccess(JSONObject jsonResult) {
        try {
            if (jsonResult != null && !jsonResult.getBoolean("error")) {
//                final int avatar = jsonResult.getInt("color");
//                final String name = jsonResult.getString("name");
//                final String email = jsonResult.getString("email");
//                final String created = jsonResult.getString("createdAt");
                this.apiKey = jsonResult.getString("apikey");
                Log.d("RESPONSE", "APIKEY " + this.apiKey);
                this.resultListener.onResult(Request.RESULT_OK);
            }
        } catch (JSONException e) {
            this.resultListener.onResult(Request.RESULT_ERR);
            e.printStackTrace();
        }
    }

    public String getApiKey() {
        return this.apiKey;
    }
}
