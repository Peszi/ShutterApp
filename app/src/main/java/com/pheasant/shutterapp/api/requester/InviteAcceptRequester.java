package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.request.InviteAcceptRequest;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.listeners.InvitesAcceptListener;
import com.pheasant.shutterapp.api.util.InviteDataHolder;
import com.pheasant.shutterapp.api.util.Request;

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
        this.inviteRequest.setRequestListener(this);
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