package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.listeners.PhotoRemoveListener;
import com.pheasant.shutterapp.api.request.PhotoRemoveRequest;
import com.pheasant.shutterapp.api.util.Request;

import java.util.LinkedList;

/**
 * Created by Peszi on 2017-12-04.
 */

public class PhotoRemoveRequester implements RequestResultListener {

    private LinkedList<Integer> requestsBuffer;

    private PhotoRemoveRequest removeRequest;
    private PhotoRemoveListener removeListener;

    public PhotoRemoveRequester(String apiKey) {
        this.requestsBuffer = new LinkedList<>();
        this.removeRequest = new PhotoRemoveRequest(apiKey);
        this.removeRequest.setRequestListener(this);
    }

    public void setListener(PhotoRemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    public void deletePhoto(int photoId) {
        this.requestsBuffer.add(photoId);
        this.checkStack();
    }

    private void checkStack() {
        if (!this.requestsBuffer.isEmpty()) {
            this.removeRequest.setPhotoId(this.requestsBuffer.poll());
        }
    }

    @Override
    public void onRequestResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            if (this.removeListener != null)
                this.removeListener.onPhotoRemove(this.removeRequest.getPhotoId());
            this.checkStack();
        }
    }
}