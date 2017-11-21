package com.pheasant.shutterapp.shutter.ui.features.manage.strangers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.InviteRequestButton;
import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-20.
 */

public class StrangerObject implements View.OnClickListener {

    private StrangerData strangerData;

    public StrangerObject(StrangerData strangerData) {
        this.strangerData = strangerData;
    }

    public void setupView(View view) {
        if (this.strangerData != null) {
            final ImageView avatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView name = (TextView) view.getTag(R.id.friend_name);
            final ToggleButton inviteBtn = (ToggleButton) view.getTag(R.id.friend_invite_btn);
            avatar.setImageResource(R.drawable.avatar_default);
            name.setText(this.strangerData.getName());
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
    public void onClick(View v) {
//        if (this.strangerData != null)
//            Log.d("RESPONSE", "[friend delete] with " + this.strangerData.getName());
    }
}
