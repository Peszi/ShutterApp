package com.pheasant.shutterapp.shutter.api.requester;

import com.pheasant.shutterapp.network.request.friends.InviteFriendRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListener;
import com.pheasant.shutterapp.shutter.api.util.InviteHolder;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-22.
 */

public class InviteManageRequester implements RequestResultListener {

    private LinkedList<InviteHolder> requestsBuffer;

    private InviteFriendRequest inviteRequest;
    private InvitesListener invitesListener;

    public InviteManageRequester(String apiKey) {
        this.requestsBuffer = new LinkedList<>();
        this.inviteRequest = new InviteFriendRequest(apiKey);
        this.inviteRequest.setOnRequestResultListener(this);
    }

    public void setListener(InvitesListener invitesListener) {
        this.invitesListener = invitesListener;
    }

    public void sendInvite(int userId) {
        this.requestsBuffer.add(new InviteHolder(userId, true));
        this.checkStack();
    }

    public void deleteInvite(int userId) {
        this.requestsBuffer.add(new InviteHolder(userId, false));
        this.checkStack();
    }

    private void checkStack() {
        if (!this.requestsBuffer.isEmpty()) {
            this.inviteRequest.sendRequest(this.requestsBuffer.poll());
            this.inviteRequest.execute();
        }
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            if (this.invitesListener != null) {
                if (this.inviteRequest.getInviteHolder().isToSend())
                    this.invitesListener.onInviteSent(this.inviteRequest.getInviteHolder().getUserId());
                else
                    this.invitesListener.onInviteDeleted(this.inviteRequest.getInviteHolder().getUserId());
            }
            this.checkStack();
        }
    }
}
