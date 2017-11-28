package com.pheasant.shutterapp.shutter.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Peszi on 2017-11-28.
 */

public class RequestProperties {

    private final String SERVER_ADDRESS = "http://shutterapp.pl/shutter/v1/";
    private final int DEFAULT_TIMEOUT = 5000;

    private String address;
    private RequestMethod requestMethod;
    private String authorizationKey;
    private List<String> parameters;
    private int timeout;

    public RequestProperties(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        this.authorizationKey = "";
        this.parameters = new ArrayList<>();
        this.timeout = this.DEFAULT_TIMEOUT;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setAuthorizationKey(String apiKey) {
        this.authorizationKey = apiKey;
    }

    public void clearParameters() { this.parameters.clear(); }

    public void addParameter(String key, String value) { this.parameters.add(key + "=" + value); }

    public void setTimeout(int timeout) { this.timeout = timeout; }

    public String getAddress() { return this.SERVER_ADDRESS + this.address; }

    public String getRequestMethod() {
        switch (this.requestMethod) {
            case GET: return "GET";
            case PUT: return "PUT";
            case POST: return "POST";
            case DELETE: return "DELETE";
        }
        return "GET";
    }

    public String getAuthorizationKey() { return this.authorizationKey; }

    public byte[] getParameters() {
        if (!this.parameters.isEmpty()) {
            String paramsChain = "";
            for (int i = 0; i < this.parameters.size()-1; i++)
                paramsChain += this.parameters.get(i) + "&";
            paramsChain += this.parameters.get(this.parameters.size()-1);
            return paramsChain.getBytes();
        }
        return null;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public boolean hasAuthorization() { return (this.authorizationKey.isEmpty() ? false : true); }

    public boolean hasParams() { return (this.parameters.isEmpty() ? false : true); }
}
