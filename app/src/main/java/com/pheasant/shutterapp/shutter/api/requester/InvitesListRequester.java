package com.pheasant.shutterapp.shutter.api.requester;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.friends.InvitesListRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class InvitesListRequester implements RequestResultListener {

    private ArrayList<UserData> invitesList;

    private InvitesListRequest listRequest;
    private InvitesListListener listListener;

    public InvitesListRequester(String apiKey) {
        this.invitesList = new ArrayList<>();
        this.listRequest = new InvitesListRequest(apiKey);
        this.listRequest.setOnRequestResultListener(this);
    }

    public void setListListener(InvitesListListener listListener) {
        this.listListener = listListener;
    }

    public void downloadList() {
        this.listRequest.cancel();
        this.listRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            ArrayList<UserData> addedInvitesList = new ArrayList<>();
            for (UserData newInvite : this.listRequest.getInvitesList()) {
                if (!this.updateInvite(newInvite)) {// if users is NOT in our list
                    this.invitesList.add(newInvite);
                    addedInvitesList.add(newInvite);
                }
            }
            this.notifyListeners(addedInvitesList);
        }
    }

    // Updating invite data if user already exist
    private boolean updateInvite(UserData updatedInvite) {
        for (UserData invite : this.invitesList)
            if (invite.getId() == updatedInvite.getId()) {
                invite.update(updatedInvite);
                return true;
            }
        return false;
    }

    private void notifyListeners(ArrayList<UserData> invitesList) {
        if (this.listListener != null)
            this.listListener.onInvitesListDownloaded(invitesList);
    }

    public ArrayList<UserData> getInvitesList() {
        return this.invitesList;
    }
}
