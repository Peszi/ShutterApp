package com.pheasant.shutterapp.api.listeners;

/**
 * Created by Peszi on 2017-11-22.
 */

public interface InvitesCreateListener {
    void onInviteRequestCreate(int userId);
    void onInviteRequestRemove(int userId);
}
