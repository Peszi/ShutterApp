package com.pheasant.shutterapp.api.util;

import com.pheasant.shutterapp.api.listeners.RequestResultListener;

/**
 * Created by Peszi on 2017-12-04.
 */

public abstract class BaseRequest implements RequestListener {

    private RequestProperties requestProperties;
    private JsonAsyncRequest jsonRequest;

    protected RequestResultListener resultListener;

    public BaseRequest(RequestMethod requestMethod) {
        this.requestProperties = new RequestProperties(requestMethod);
    }

    public void setRequestListener(RequestResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public void sendRequest() {
        if (this.jsonRequest != null)
            this.jsonRequest.cancel(true);
        this.jsonRequest = new JsonAsyncRequest();
        this.jsonRequest.setRequestListener(this);
        this.jsonRequest.execute(this.requestProperties);
    }

    public void cancelRequest() {
        if (this.jsonRequest != null)
            this.jsonRequest.cancel(true);
    }

    public RequestProperties getProperties() { return this.requestProperties; }
}
