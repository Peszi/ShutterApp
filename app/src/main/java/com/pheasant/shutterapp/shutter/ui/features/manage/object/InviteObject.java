package com.pheasant.shutterapp.shutter.ui.features.manage.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.shutter.ui.shared.LoadingButton;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-21.
 */

public class InviteObject implements View.OnClickListener {

    private UserData inviteData;

    private InviteAcceptBtnListener objectListener;

    public InviteObject(UserData inviteData) {
        this.inviteData = inviteData;
    }

    public void setupView(View view) {
        if (inviteData != null) {
            final ImageView friendAvatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView friendName = (TextView) view.getTag(R.id.friend_name);
            final LoadingButton acceptBtn = (LoadingButton) view.getTag(R.id.friend_accept_btn);
            final LoadingButton rejectBtn = (LoadingButton) view.getTag(R.id.friend_reject_btn);
            friendAvatar.setImageResource(Avatar.getAvatar(this.inviteData.getAvatar()));
            friendName.setText(this.inviteData.getName());
            acceptBtn.initButton();
            rejectBtn.initButton();
            acceptBtn.setButtonListener(this);
            rejectBtn.setButtonListener(this);
        }
    }

    public void setObjectListener(InviteAcceptBtnListener objectListener) {
        this.objectListener = objectListener;
    }

    public static View getView(Context context, LayoutInflater layoutInflater, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.layout_invite, parent, false);
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_accept_btn, view.findViewById(R.id.friend_accept_btn));
            view.setTag(R.id.friend_reject_btn, view.findViewById(R.id.friend_reject_btn));
        }
        Util.setupFont(context, view, Util.FONT_PATH_LIGHT);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (this.inviteData != null && this.objectListener != null) {
            switch (v.getId()) {
                case R.id.friend_accept_btn:
                    this.objectListener.onInviteAcceptEvent(this.inviteData.getId()); break;
                case R.id.friend_reject_btn:
                    this.objectListener.onInviteRejectEvent(this.inviteData.getId()); break;
            }
        }
    }

    public interface InviteAcceptBtnListener {
        void onInviteAcceptEvent(int userId);
        void onInviteRejectEvent(int userId);
    }
}
