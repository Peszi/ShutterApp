package com.pheasant.shutterapp.shutter.api.requester;

import com.pheasant.shutterapp.shutter.api.request.RemoveFriendRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.listeners.FriendRemoveListener;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-11-22.
 */

public class FriendRemoveRequester implements RequestResultListener {

    private LinkedList<Integer> requestsBuffer;

    private RemoveFriendRequest removeRequest;
    private FriendRemoveListener removeListener;

    public FriendRemoveRequester(String apiKey) {
        this.requestsBuffer = new LinkedList<>();
        this.removeRequest = new RemoveFriendRequest(apiKey);
        this.removeRequest.setOnRequestResultListener(this);
    }

    public void setListener(FriendRemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    public void deleteFriend(int userId) {
        this.requestsBuffer.add(userId);
        this.checkStack();
    }

    private void checkStack() {
        if (!this.requestsBuffer.isEmpty()) {
            this.removeRequest.setFriendId(this.requestsBuffer.poll());
            this.removeRequest.execute();
        }
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
