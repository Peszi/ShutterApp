package com.pheasant.shutterapp.network.request.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Peszi on 2017-05-24.
 */

public class BitmapRequest extends BaseRequest {

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            URL requestUrl = new URL(this.SERVER_ADDRESS + this.requestHolder.getAddress());
            HttpURLConnection requestConnection = (HttpURLConnection) requestUrl.openConnection();
            requestConnection.setRequestMethod(this.requestHolder.getMethod());
            if (this.requestHolder.isAuthorization())
                requestConnection.setRequestProperty("Authorization", this.requestHolder.getAuthorizationKey());
            if (this.requestHolder.hasParameters()) {
                requestConnection.setDoOutput(true);
                requestConnection.getOutputStream().write(this.requestHolder.getParameters().getBytes());
            }
            requestConnection.setDoInput(true);
            requestConnection.connect();
            if (requestConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return BitmapFactory.decodeStream(requestConnection.getInputStream());
            }
            requestConnection.disconnect();
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
        this.requestHolder.onSuccess(result);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

}