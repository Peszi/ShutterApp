package com.pheasant.shutterapp.network.request.util;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Peszi on 2017-04-20.
 */

public class JsonRequest extends BaseRequest {

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL requestUrl = new URL(this.SERVER_ADDRESS + this.requestHolder.getAddress());
            HttpURLConnection requestConnection = (HttpURLConnection) requestUrl.openConnection();
            requestConnection.setRequestMethod(this.requestHolder.getMethod());
            requestConnection.setConnectTimeout(5000);
            if (this.requestHolder.isAuthorization())
                requestConnection.setRequestProperty("Authorization", this.requestHolder.getAuthorizationKey());
            if (this.requestHolder.hasParameters()) {
                requestConnection.setDoOutput(true);
                requestConnection .getOutputStream().write(this.requestHolder.getParameters().getBytes());
            }
            BufferedReader bufferedReader;
            if (requestConnection.getResponseCode() == HttpURLConnection.HTTP_OK | requestConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                bufferedReader = new BufferedReader(new InputStreamReader(requestConnection.getInputStream(), "UTF-8"));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(requestConnection.getErrorStream(), "UTF-8"));
            }
            String line, response = "";
            while ((line = bufferedReader.readLine()) != null)
                response += line;
            bufferedReader.close();
            requestConnection.disconnect();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (result != null)
            this.requestHolder.onSuccess(result);
        // TODO
//        else
//            this.requestHolder.e
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }
}
