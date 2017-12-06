package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.request.InviteCreateRequest;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.listeners.InvitesCreateListener;
import com.pheasant.shutterapp.api.util.InviteDataHolder;
import com.pheasant.shutterapp.api.util.Request;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-22.
 */

public class InviteCreateRequester implements RequestResultListener {

    private LinkedList<InviteDataHolder> requestsBuffer;

    private InviteCreateRequest inviteRequest;
    private InvitesCreateListener invitesCreateListener;

    public InviteCreateRequester(String apiKey) {
        this.requestsBuffer = new LinkedList<>();
        this.inviteRequest = new InviteCreateRequest(apiKey);
        this.inviteRequest.setRequestListener(this);
    }

    public void setListener(InvitesCreateListener invitesCreateListener) {
        this.invitesCreateListener = invitesCreateListener;
    }

    public void sendInvitation(int userId) {
        this.requestsBuffer.add(new InviteDataHolder(userId, true));
        this.checkStack();
    }

    public void removeInvitation(int userId) {
        this.requestsBuffer.add(new InviteDataHolder(userId, false));
        this.checkStack();
    }

    private void checkStack() {
        if (!this.requestsBuffer.isEmpty()) {
            this.inviteRequest.sendRequest(this.requestsBuffer.poll());
        }
    }

    @Override
    public void onRequestResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            if (this.invitesCreateListener != null) {
                if (this.inviteRequest.getInviteDataHolder().isNotRemoving())
                    this.invitesCreateListener.onInviteRequestCreate(this.inviteRequest.getInviteDataHolder().getUserId());
                else
                    this.invitesCreateListener.onInviteRequestRemove(this.inviteRequest.getInviteDataHolder().getUserId());
            }
            this.checkStack();
        }
    }
}
