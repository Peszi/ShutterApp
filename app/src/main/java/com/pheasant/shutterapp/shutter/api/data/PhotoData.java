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

    public void setCreatedDate(String lastActivity) {
        this.createdDate = TimeStamp.getTimeDate(lastActivity);
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

//    public static String getLiveTime(Date createdAt) {
//        if (createdAt != null) {
//            Date currentTime = new Date();
//            long minutes = Math.abs((currentTime.getTime() - createdAt.getTime()) / 1000 / 60);
//            long hours = minutes / 60;
//            long days = hours / 24;
//            Log.d("RESPONSE", " M " + minutes);
//            if (days > 0)
//                return days + "d";
//            if (hours > 0)
//                return hours + "h";
//            return minutes + "m";
//        }
//        return "err";
//    }
}