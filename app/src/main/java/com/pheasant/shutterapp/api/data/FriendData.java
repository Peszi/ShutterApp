package com.pheasant.shutterapp.api.data;

import com.pheasant.shutterapp.ui.util.TimeStamp;

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
