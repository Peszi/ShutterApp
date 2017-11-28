package com.pheasant.shutterapp.shutter.api.data;

/**
 * Created by Peszi on 2017-06-12.
 */

public class StrangerData extends UserData {

    private int invite;

    public void setInvite(int invite) {
        this.invite = invite;
    }

    public int getInvite() {
        return this.invite;
    }

    public boolean isInvited() {
        return this.invite == 1 ? true : false;
    }

    public boolean hasInvitation() {
        return this.invite == 2 ? true : false;
    }
}
