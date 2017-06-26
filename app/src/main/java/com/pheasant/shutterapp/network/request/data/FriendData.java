package com.pheasant.shutterapp.network.request.data;

import com.pheasant.shutterapp.utils.TimeStamp;

import java.util.Date;

/**
 * Created by Peszi on 2017-06-16.
 */

public class FriendData extends UserData {

    private Date lastActivity;

    public void setLastActivity(String lastActivity) {
        this.lastActivity = TimeStamp.getTimeDate(lastActivity);
    }

    public String getLastActivity() {
        if (this.lastActivity != null)
            return TimeStamp.getLiveTime(this.lastActivity);
        return "no activities";
    }

}
