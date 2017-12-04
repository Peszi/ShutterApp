package com.pheasant.shutterapp.api.data;

import com.pheasant.shutterapp.ui.util.TimeStamp;

import java.util.Date;

/**
 * Created by Peszi on 2017-05-05.
 */

public class PhotoData {

    private int imageId;
    private int creatorId;
    private String creatorName;
    private boolean isMe;
    private String stringTime;
    private Date createdTime;

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

    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }

    public void setCreatedTime(String stringTime) {
        this.stringTime = stringTime;
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

    public boolean isMe() { return this.isMe; }

    public String getLiveTime() {
        if (this.createdTime == null)
            this.createdTime = TimeStamp.getTimeDate(this.stringTime);
        return TimeStamp.getLiveTime(this.createdTime);
    }

}