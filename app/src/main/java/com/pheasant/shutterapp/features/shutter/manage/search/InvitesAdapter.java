package com.pheasant.shutterapp.features.shutter.manage.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.ReloadController;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.friends.InvitesListRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.Util;

import java.util.List;

/**
 * Created by Peszi on 2017-06-09.
 */

public class InvitesAdapter extends ArrayAdapter<UserData> implements RequestResultListener, View.OnClickListener, ReloadController {

    private InvitesListRequest invitesRequest;
    private LayoutInflater layoutInflater;
    private String apiKey;

    public InvitesAdapter(Context context, Bundle bundle) {
        super(context, R.layout.layout_recipient);
        this.apiKey = bundle.getString(IntentKey.USER_API_KEY);
        this.layoutInflater = LayoutInflater.from(context);
        this.invitesRequest = new InvitesListRequest(this.apiKey);
        this.invitesRequest.setOnRequestResultListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = this.layoutInflater.inflate(R.layout.layout_invite, parent, false);
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_accept_btn, view.findViewById(R.id.friend_accept_btn));
            view.setTag(R.id.friend_reject_btn, view.findViewById(R.id.friend_reject_btn));
        }
        Util.setupFont(this.getContext(), view, Util.FONT_PATH_LIGHT);
        UserData userData = this.getItem(position);
        if (userData != null) {
            final ImageView friendAvatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView friendName = (TextView) view.getTag(R.id.friend_name);
            InvitationRequestButton acceptBtn = (InvitationRequestButton) view.getTag(R.id.friend_accept_btn);
            InvitationRequestButton rejectBtn = (InvitationRequestButton) view.getTag(R.id.friend_reject_btn);
            friendAvatar.setImageResource(Avatar.getAvatar(userData.getAvatar()));
            friendName.setText(userData.getName());
            acceptBtn.setup(this, this.apiKey, userData.getId(), true);
            rejectBtn.setup(this, this.apiKey, userData.getId(), false);
        }
        return view;
    }

    public void download() {
        this.invitesRequest.execute();
    }

    private void reloadList(List<UserData> friendsList) {
        this.clear();
        if (friendsList != null)
            this.addAll(friendsList);
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResult(int resultCode) {
//        if (resultCode == Request.RESULT_OK) {
//            this.reloadList(this.invitesRequest.getFriendsList());
//        }
    }

    @Override
    public void onReload() {
        this.invitesRequest.execute();
    }
}