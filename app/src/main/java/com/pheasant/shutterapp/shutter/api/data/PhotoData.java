package com.pheasant.shutterapp.shutter.api.data;

import com.pheasant.shutterapp.utils.TimeStamp;

import java.util.Date;

/**
 * Created by Peszi on 2017-05-05.
 */

public class PhotoData {

    private int imageId;
    private int creatorId;
    private String creatorName;
    private Date createdDate;

    // Setters

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatedDate(String stringDate) {
        this.createdDate = TimeStamp.getTimeDate(stringDate);
    }

    // Getters

    public int getImageId() {
        return this.imageId;
    }

    public int getCreatorId() {
        return this.creatorId;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public Date getDate() {
        return this.createdDate;
    }

}