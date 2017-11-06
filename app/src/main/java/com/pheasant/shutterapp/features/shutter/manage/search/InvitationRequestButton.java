package com.pheasant.shutterapp.features.shutter.manage.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.pheasant.shutterapp.features.shutter.manage.friends.ReloadController;
import com.pheasant.shutterapp.network.request.friends.UserInviteRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;

/**
 * Created by Peszi on 2017-06-17.
 */

public class InvitationRequestButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener, RequestResultListener {

    private UserInviteRequest userInviteRequest;
    private ReloadController reloadController;

    public InvitationRequestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }

    public void setup(ReloadController reloadController, String apiKey, int id, boolean accept) {
        this.reloadController = reloadController;
        this.userInviteRequest = new UserInviteRequest(apiKey, id);
        this.userInviteRequest.setOnRequestResultListener(this);
        if (accept)
            this.userInviteRequest.setToPost();
        else
            this.userInviteRequest.setToDelete();
    }

    @Override
    public void onClick(View v) {
        this.userInviteRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            Toast.makeText(this.getContext().getApplicationContext(), "successfully", Toast.LENGTH_LONG).show();
            this.reloadController.onReload();
        } else {
            Toast.makeText(this.getContext().getApplicationContext(), "error! try again", Toast.LENGTH_LONG).show();
        }
    }
}
