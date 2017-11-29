package com.pheasant.shutterapp.shutter.api.data;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.util.PhotoUtility;

import java.util.List;

/**
 * Created by Peszi on 2017-11-29.
 */

public class UploadPhotoData {

    private Bitmap photoData;
    private List<Integer> recipientsList;

    public UploadPhotoData(Bitmap photoData, List<Integer> recipientsList) {
        this.photoData = photoData;
        this.recipientsList = recipientsList;
    }

    public String getUserPhotoData() {
        return PhotoUtility.getBase64Image(this.photoData);
    }

    public List<Integer> getRecipientsList() {
        return this.recipientsList;
    }

}
