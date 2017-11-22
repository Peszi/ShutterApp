package com.pheasant.shutterapp.shutter.api.util;

/**
 * Created by Peszi on 2017-11-22.
 */

public class InviteHolder {

    private int userId;
    private boolean sendInvite;

    public InviteHolder(int userId, boolean sendInvite) {
        this.userId = userId;
        this.sendInvite = sendInvite;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isToSend() {
        return this.sendInvite;
    }
}
