package com.pheasant.shutterapp.shutter.api.requester;

import com.pheasant.shutterapp.shutter.api.request.InviteAcceptRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesAcceptListener;
import com.pheasant.shutterapp.shutter.api.util.InviteDataHolder;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-23.
 */

public class InviteAcceptRequester implements RequestResultListener {

    private LinkedList<InviteDataHolder> requestsBuffer;

    private InviteAcceptRequest inviteRequest;
    private InvitesAcceptListener invitesAcceptListener;

    public InviteAcceptRequester(String apiKey) {
        this.requestsBuffer = new LinkedList<>();
        this.inviteRequest = new InviteAcceptRequest(apiKey);
        this.inviteRequest.setOnRequestResultListener(this);
    }

    public void setListener(InvitesAcceptListener invitesAcceptListener) {
        this.invitesAcceptListener = invitesAcceptListener;
    }

    public void acceptInvite(int userId) {
        this.requestsBuffer.add(new InviteDataHolder(userId, true));
        this.checkStack();
    }

    public void rejectInvite(int userId) {
        this.requestsBuffer.add(new InviteDataHolder(userId, false));
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
            if (this.invitesAcceptListener != null) {
                if (this.inviteRequest.getInviteDataHolder().isNotRemoving())
                    this.invitesAcceptListener.onInviteRequestAccepted(this.inviteRequest.getInviteDataHolder().getUserId());
                else
                    this.invitesAcceptListener.onInviteRequestRemoved(this.inviteRequest.getInviteDataHolder().getUserId());
            }
            this.checkStack();
        }
    }
}