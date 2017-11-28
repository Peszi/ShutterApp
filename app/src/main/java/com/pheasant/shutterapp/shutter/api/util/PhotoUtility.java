package com.pheasant.shutterapp.shutter.api.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Peszi on 2017-11-28.
 */

public class PhotoUtility {

    private static final int PHOTO_COMPRESSION = 80;

    public static String getBase64Image(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, PhotoUtility.PHOTO_COMPRESSION, outputStream);
        bitmap.recycle();
        String encodedImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        try {
            return URLEncoder.encode(encodedImage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
