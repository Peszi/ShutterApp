package com.pheasant.shutterapp.api.util;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Peszi on 2017-12-04.
 */

public class JsonAsyncRequest extends AsyncTask<RequestProperties, Integer, JSONObject> {

    private RequestListener requestListener;

    protected void sendRequest(RequestProperties requestProperties) {
        this.execute(requestProperties);
    }

    protected void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    @Override
    protected JSONObject doInBackground(RequestProperties... requestProperties) {
        return RequestUtility.makeJSONRequest(requestProperties[0]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonResult) {
        if (this.requestListener != null)
            this.requestListener.onSuccess(jsonResult);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

}
