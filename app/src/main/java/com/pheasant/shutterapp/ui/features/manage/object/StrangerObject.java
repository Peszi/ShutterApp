package com.pheasant.shutterapp.ui.features.manage.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.data.StrangerData;
import com.pheasant.shutterapp.ui.shared.LoadingToggleButton;
import com.pheasant.shutterapp.util.Util;

/**
 * Created by Peszi on 2017-11-20.
 */

public class StrangerObject implements LoadingToggleButton.OnToggleClickListener {

    private StrangerData strangerData;

    private InviteCreateBtnListener objectListener;

    public StrangerObject(StrangerData strangerData) {
        this.strangerData = strangerData;
    }

    public void setObjectListener(InviteCreateBtnListener objectListener) {
        this.objectListener = objectListener;
    }

    public void setupView(View view) {
        if (this.strangerData != null) {
            final ImageView avatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView name = (TextView) view.getTag(R.id.friend_name);
            final LoadingToggleButton inviteBtn = (LoadingToggleButton) view.getTag(R.id.friend_invite_btn);
            avatar.setImageResource(R.drawable.avatar_default);
            name.setText(this.strangerData.getName());
            inviteBtn.setButtonState(this.strangerData.getInvite());
            inviteBtn.setListener(this);
        }
    }

    public static View getView(Context context, LayoutInflater layoutInflater, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.layout_stranger, parent, false);
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_invite_btn, view.findViewById(R.id.friend_invite_btn));
        }
        Util.setupFont(context, view, Util.FONT_PATH_LIGHT);
        return view;
    }

    @Override
    public void onClickEvent(int state, View view) {
        if (this.objectListener != null) {
            final int userId = this.strangerData.getId();
            switch (state) {
                case 0: this.objectListener.onInviteCreateEvent(userId); break;
                case 1: this.objectListener.onInviteRemoveEvent(userId); break;
            }
        }
    }

    public interface InviteCreateBtnListener {
        void onInviteCreateEvent(int userId);
        void onInviteRemoveEvent(int userId);
    }
}
