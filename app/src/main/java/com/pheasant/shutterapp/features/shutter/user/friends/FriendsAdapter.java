package com.pheasant.shutterapp.features.shutter.user.friends;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-20.
 */

public class FriendsAdapter extends ArrayAdapter<FriendData> implements FriendsListener, ReloadController {

    private List<FriendData> friendsList = new ArrayList<>();
    private UserFriendsManager friendsManager;
    private LayoutInflater layoutInflater;
    private String apiKey;

    public FriendsAdapter(Context context, Bundle bundle) {
        super(context, R.layout.layout_recipient);
        this.apiKey = bundle.getString(IntentKey.USER_API_KEY);
        this.layoutInflater = LayoutInflater.from(context);
        this.friendsManager = new UserFriendsManager(this.apiKey);
        this.friendsManager.setFriendsListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View baseView = this.getView(convertView, parent);
        this.setupView(baseView, position);
        return baseView;
    }

    private View getView(View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = this.layoutInflater.inflate(R.layout.layout_friend, parent, false);
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_activity, view.findViewById(R.id.friend_activity));
            view.setTag(R.id.friend_remove_btn, view.findViewById(R.id.friend_remove_btn));
        }
        Util.setupFont(this.getContext(), view, Util.FONT_PATH_LIGHT);
        return view;
    }

    private void setupView(View view, int position) {
        FriendData userData = this.getItem(position);
        if (userData != null) {
            final ImageView avatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView name = (TextView) view.getTag(R.id.friend_name);
            final TextView lastActivity = (TextView) view.getTag(R.id.friend_activity);
            final RemoveRequestButton removeBtn = (RemoveRequestButton) view.getTag(R.id.friend_remove_btn);
            avatar.setImageResource(Avatar.getAvatar(userData.getAvatar()));
            name.setText(userData.getName());
            lastActivity.setText(userData.getLastActivity());
            removeBtn.setup(this, this.apiKey, userData.getId(), userData.getName());
        }
    }

    public void download() {
        this.friendsManager.reload();
    }

    public void setFilter(String keyword) {
        this.clear();
        for (int i = 0; i < this.friendsList.size(); i++) {
            if (this.friendsList.get(i).getName().contains(keyword))
                this.add(this.friendsList.get(i));
        }
        this.notifyDataSetChanged();
        if (this.getCount() == 0) {

        }
    }

    private void reloadList(List<FriendData> friendsList) { // TODO list (remove not existing obj)
        this.clear();
        if (friendsList != null)
            this.addAll(friendsList);
        this.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(List<FriendData> friendsList) {
        this.friendsList.clear();
        this.friendsList.addAll(friendsList);
        this.reloadList(this.friendsList);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this.getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReload() {
        this.friendsManager.reload();
    }
}
//this.showRemoveFriendDialog(((TextView) baseView.findViewById(R.id.friend_name)).getText().toString(), 0);