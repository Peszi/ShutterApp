package com.pheasant.shutterapp.shutter.ui.shared;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.ReloadController;
import com.pheasant.shutterapp.network.request.friends.InviteFriendRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;

/**
 * Created by Peszi on 2017-11-22.
 */

public class InviteTmpButton extends LinearLayout implements View.OnClickListener {

    private TextView textView;
    private ProgressBar progressBar;

    private InviteButtonListener inviteListener;

    private int state;

    public InviteTmpButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_button_invite, this);
        this.setBackgroundResource(R.drawable.all_dark_set_btn_back);
        this.textView = (TextView) this.findViewById(R.id.invite_btn_text);
        this.progressBar = (ProgressBar) this.findViewById(R.id.invite_btn_progress);
        this.setOnClickListener(this);
    }

    public void setListener(InviteButtonListener inviteListener) {
        this.inviteListener = inviteListener;
    }

    public void setState(int state) {
        switch (state) {
            case 0: this.setUninvited(); break;
            case 1: this.setInvited(); break;
        }
    }

    public void setInvited() {
        this.state = 1;
        this.setButtonState();
        this.textView.setText("invited");
        this.textView.setTextColor(Color.WHITE);
        this.setBackgroundResource(R.drawable.all_dark_reset_btn_back);
    }

    public void setUninvited() {
        this.state = 0;
        this.setButtonState();
        this.textView.setText("invite");
        this.textView.setTextColor(Color.BLACK);
        this.setBackgroundResource(R.drawable.all_dark_set_btn_back);
    }

    private void setButtonState() {
        this.textView.setVisibility(VISIBLE);
        this.progressBar.setVisibility(GONE);
    }

    private void setLoadingState(int color) {
        this.textView.setVisibility(GONE);
        this.progressBar.setVisibility(VISIBLE);
        this.progressBar.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onClick(View v) {
        this.notifyListener();
        if (this.state < 2) {
            int spinnerColor = Color.BLACK;
            if (this.state == 1)
                spinnerColor = Color.WHITE;
            this.setLoadingState(spinnerColor);
            this.state = 2;
        }
    }

    private void notifyListener() {
        if (this.inviteListener != null) {
            switch (this.state) {
                case 0: this.inviteListener.onInvite(); break;
                case 1: this.inviteListener.onUndo(); break;
            }
        }
    }

    public interface InviteButtonListener {
        void onInvite();
        void onUndo();
    }
}