package com.pheasant.shutterapp.shutter.api.listeners;

import com.pheasant.shutterapp.network.request.data.UserData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public interface InvitesListListener {
    void onInvitesListDownloaded(ArrayList<UserData> invitesList);
}
