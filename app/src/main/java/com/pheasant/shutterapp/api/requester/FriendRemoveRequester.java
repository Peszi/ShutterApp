package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.request.FriendRemoveRequest;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.listeners.FriendRemoveListener;
import com.pheasant.shutterapp.api.util.Request;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-22.
 */

public class FriendRemoveRequester implements RequestResultListener {

    private LinkedList<Integer> requestsBuffer;

    private FriendRemoveRequest removeRequest;
    private FriendRemoveListener removeListener;

    public FriendRemoveRequester(String apiKey) {
        this.requestsBuffer = new LinkedList<>();
        this.removeRequest = new FriendRemoveRequest(apiKey);
        this.removeRequest.setRequestListener(this);
    }

    public void setListener(FriendRemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    public void deleteFriend(int userId) {
        this.requestsBuffer.add(userId);
        this.checkStack();
    }

    private void checkStack() {
        if (!this.requestsBuffer.isEmpty())
            this.removeRequest.setFriendId(this.requestsBuffer.poll());
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            if (this.removeListener != null)
                this.removeListener.onFriendRemove(this.removeRequest.getFriendId());
            this.checkStack();
        }
    }
}
