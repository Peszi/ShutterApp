package com.pheasant.shutterapp.shutter.ui.features.manage.friends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.RemoveRequestButton;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-09.
 */

public class FriendObject implements View.OnClickListener {

    private FriendData friendData;

    public FriendObject(FriendData friendData) {
        this.friendData = friendData;
    }

    public void setupView(View view) {
        if (friendData != null) {
            final ImageView avatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView name = (TextView) view.getTag(R.id.friend_name);
            final TextView lastActivity = (TextView) view.getTag(R.id.friend_activity);
            final ImageButton removeBtn = (ImageButton) view.getTag(R.id.friend_remove_btn);
            avatar.setImageResource(Avatar.getAvatar(friendData.getAvatar()));
            name.setText(friendData.getName());
            lastActivity.setText(friendData.getLastActivity());
            removeBtn.setOnClickListener(this);
        }
    }

    public static View getView(Context context, LayoutInflater layoutInflater, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.layout_friend, parent, false);
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_activity, view.findViewById(R.id.friend_activity));
            view.setTag(R.id.friend_remove_btn, view.findViewById(R.id.friend_remove_btn));
        }
        Util.setupFont(context, view, Util.FONT_PATH_LIGHT);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (this.friendData != null)
            Log.d("RESPONSE", "[friend delete] with " + this.friendData.getName());
    }
}
