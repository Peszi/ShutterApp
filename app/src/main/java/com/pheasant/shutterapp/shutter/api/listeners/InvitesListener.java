package com.pheasant.shutterapp.shutter.api.listeners;

/**
 * Created by Peszi on 2017-11-22.
 */

public interface InvitesListener {
    void onInviteSent(int userId);
    void onInviteDeleted(int userId);
}
