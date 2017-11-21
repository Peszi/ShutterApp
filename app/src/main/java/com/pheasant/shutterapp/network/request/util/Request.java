package com.pheasant.shutterapp.network.request.util;

import android.util.Log;

import com.pheasant.shutterapp.shutter.api.util.FriendsRequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-04-21.
 */

public abstract class Request {

    public static final int RESULT_OK = 0;
    public static final int RESULT_ERR = -1;

    private BaseRequest requestTask;

    private int output;
    private String address;
    private String argument;
    private String method;
    private String authorization;
    private List<String> parameters = new ArrayList<String>();

    private FriendsRequestListener friendsRequestListener;
    private RequestResultListener resultListener;

    public void execute() {
        this.requestTask = BaseRequest.getBaseRequest(this.output, this);
        this.requestTask.execute();
    }

    public void setFriendsRequestListener(FriendsRequestListener friendsRequestListener) {
        Log.d("RESPONSE", "setting listener " + (friendsRequestListener != null ? true : false));
        this.friendsRequestListener = friendsRequestListener;
    }

    protected FriendsRequestListener getFriendsRequestListener() {
        return this.friendsRequestListener;
    }

    public void setOnRequestResultListener(RequestResultListener resultListener) {
        this.resultListener = resultListener;
    }

    protected RequestResultListener getResultListener() {
        return this.resultListener;
    }

    public void cancel() {
        if (this.requestTask != null)
            this.requestTask.cancel(true);
    }

    public void setOutputData(int output) {
        this.output = output;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected void setArgument(String argument) {
        this.argument = argument;
    }

    protected void setMethod(String method) {
        this.method = method;
    }

    protected void setAuthorization(String key) {
        this.authorization = key;
    }

    protected void addParameter(String key, String value) {
        this.parameters.add(key + "=" + value);
    }

    protected String getAddress() {
        if (this.argument != null)
            return this.address + "/" + this.argument;
        return this.address;
    }

    public String getArgument() {
        return this.argument;
    }

    protected String getMethod() {
        return this.method;
    }

    protected boolean isAuthorization() {
        return this.authorization != null ? true : false;
    }

    protected String getAuthorizationKey() {
        return this.authorization;
    }

    protected boolean hasParameters() {
        return !this.parameters.isEmpty();
    }

    protected String getParameters() {
        String parametersChain = "";
        if (this.parameters.size() >= 1) {
            parametersChain = this.parameters.get(0);
            for (int i = 1; i < this.parameters.size(); i++)
                parametersChain += "&" + this.parameters.get(i);
        }
        return parametersChain;
    }

    protected abstract void onSuccess(Object result);

}
