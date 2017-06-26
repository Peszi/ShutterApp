package com.pheasant.shutterapp.features.shutter.user.friends;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.friends.InviteFriendRequest;
import com.pheasant.shutterapp.network.request.util.OnRequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;

/**
 * Created by Peszi on 2017-06-17.
 */

public class InviteRequestButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener, OnRequestResultListener {

    private InviteFriendRequest inviteRequest;
    private ReloadController reloadController;
    private int state;

    public InviteRequestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }

    public void setup(ReloadController reloadController, String apiKey, int id) {
        this.reloadController = reloadController;
        this.inviteRequest = new InviteFriendRequest(apiKey, id);
        this.inviteRequest.setOnRequestResultListener(this);
    }

    public void setState(int state) {
        this.state = state;
        switch (this.state) {
            case 0: this.setToInvite(); break;
            case 1: this.setToInvited(); break;
        }
    }

    private void setToInvite() {
        this.setText("invite");
        this.setTextColor(Color.BLACK);
        this.setBackgroundResource(R.drawable.all_dark_set_btn_back);
    }

    private void setToInvited() {
        this.setText("invited");
        this.setTextColor(Color.WHITE);
        this.setBackgroundResource(R.drawable.all_dark_reset_btn_back);
    }

    @Override
    public void onClick(View v) {
        switch (this.state) {
            case 0: this.inviteRequest.setToPost(); break;
            case 1: this.inviteRequest.setToDelete(); break;
        }
        this.inviteRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            String message = "invited successfully";
            if (this.state == 1)
                message = "invite removed";
            Toast.makeText(this.getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            this.reloadController.onReload();
        } else {
            Toast.makeText(this.getContext().getApplicationContext(), "error! try again", Toast.LENGTH_LONG).show();
        }
    }
}
