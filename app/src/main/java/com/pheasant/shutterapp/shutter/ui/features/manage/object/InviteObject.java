package com.pheasant.shutterapp.shutter.ui.features.manage.object;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.search.InvitationRequestButton;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-21.
 */

public class InviteObject implements View.OnClickListener {

    private UserData inviteData;

    public InviteObject(UserData inviteData) {
        this.inviteData = inviteData;
    }

    public void setupView(View view) {
        if (inviteData != null) {
            final ImageView friendAvatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView friendName = (TextView) view.getTag(R.id.friend_name);
            Button acceptBtn = (Button) view.getTag(R.id.friend_accept_btn);
            Button rejectBtn = (Button) view.getTag(R.id.friend_reject_btn);
            friendAvatar.setImageResource(Avatar.getAvatar(this.inviteData.getAvatar()));
            friendName.setText(this.inviteData.getName());
        }
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
        if (this.inviteData != null)
            Log.d("RESPONSE", "[friend delete] with " + this.inviteData.getName());
    }
}
