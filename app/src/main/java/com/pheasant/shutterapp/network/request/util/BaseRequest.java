package com.pheasant.shutterapp.network.request.util;

import android.os.AsyncTask;

/**
 * Created by Peszi on 2017-05-24.
 */

public abstract class BaseRequest extends AsyncTask<Void, Integer, Object> {

    protected final String SERVER_ADDRESS = "http://92.222.70.28/shutter/v1/";

    public static final int TYPE_JSON = 0;
    public static final int TYPE_BITMAP = 1;

    protected Request requestHolder;

    private void setRequest(Request request) {
        this.requestHolder = request;
    }

    public static BaseRequest getBaseRequest(int type, Request request) {
        BaseRequest baseRequest = null;
        switch (type) {
            case TYPE_JSON: baseRequest = new JsonRequest(); break;
            case TYPE_BITMAP: baseRequest = new BitmapRequest(); break;
        }
        if (baseRequest != null)
            baseRequest.setRequest(request);
        return baseRequest;
    }
}
