package com.pheasant.shutterapp.shutter.api.util;

import android.util.Log;

import com.pheasant.shutterapp.network.request.util.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Peszi on 2017-11-28.
 */

public class RequestUtility {

    public static String makeStringRequest(RequestProperties requestProperties) {
        HttpURLConnection requestConnection = RequestUtility.prepareRequest(requestProperties);
        if (requestConnection != null)
            return RequestUtility.readConnectionStream(requestConnection);
        return null;
    }

    public static JSONObject makeJSONRequest(RequestProperties requestProperties) {
        HttpURLConnection requestConnection = RequestUtility.prepareRequest(requestProperties);
        if (requestConnection != null) {
            try {
                JSONObject jsonObject = new JSONObject(RequestUtility.readConnectionStream(requestConnection));
                return jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static HttpURLConnection prepareRequest(RequestProperties requestProperties) {
        try {
            URL requestUrl = new URL(requestProperties.getAddress());
            HttpURLConnection requestConnection = (HttpURLConnection) requestUrl.openConnection();
            requestConnection.setRequestMethod(requestProperties.getRequestMethod());
            requestConnection.setConnectTimeout(requestProperties.getTimeout());
            if (requestProperties.hasAuthorization())
                requestConnection.setRequestProperty("Authorization", requestProperties.getAuthorizationKey());
            if (requestProperties.hasParams()) {
                requestConnection.setDoOutput(true);
                requestConnection.getOutputStream().write(requestProperties.getParameters());
            }
            requestConnection.connect();
            return requestConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readConnectionStream(HttpURLConnection requestConnection) {
        try {
            InputStream inputStream = requestConnection.getErrorStream();
            if (requestConnection.getResponseCode() == HttpURLConnection.HTTP_OK | requestConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED)
                inputStream = requestConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line, response = "";
            while ((line = bufferedReader.readLine()) != null)
                response += line;
            bufferedReader.close();
            requestConnection.disconnect();
            return response;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
