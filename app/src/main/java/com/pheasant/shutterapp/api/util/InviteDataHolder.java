package com.pheasant.shutterapp.api.util;

/**
 * Created by Peszi on 2017-11-22.
 */

public class InviteDataHolder {

    private int userId;
    private boolean notRemoving;

    public InviteDataHolder(int userId, boolean notRemoving) {
        this.userId = userId;
        this.notRemoving = notRemoving;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isNotRemoving() {
        return this.notRemoving;
    }
}
