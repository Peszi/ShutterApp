package com.pheasant.shutterapp.shutter.ui.features.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.FriendObject;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.InviteObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class InvitesTmpAdapter extends ArrayAdapter<InviteObject> {

    private ArrayList<UserData> invitesList;

    private LayoutInflater layoutInflater;

    public InvitesTmpAdapter(Context context) {
        super(context, R.layout.layout_recipient);
        this.layoutInflater = LayoutInflater.from(context);
        this.invitesList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View baseView = InviteObject.getView(this.getContext(), this.layoutInflater, convertView, parent);
        this.getItem(position).setupView(baseView);
        return baseView;
    }

    // Filtering list
    public void setFilter(String keyword) {
//        if (!keyword.isEmpty()) {
//            final ArrayList<FriendData> filteredFriendsList = this.getFriendsListWithKeyword(keyword);
//            this.reloadList(filteredFriendsList);
//            if (filteredFriendsList.size() == 0)
//                this.noFriendsWithKeyword();
//        } else {
//            this.reloadList(this.invitesList);
//        }
    }

    private ArrayList<FriendData> getFriendsListWithKeyword(String keyword) {
//        ArrayList<FriendData> filteredFriendsList = new ArrayList<>();
//        for (FriendData friend : this.invitesList)
//            if (friend.getName().contains(keyword))
//                filteredFriendsList.add(friend);
        return null;//filteredFriendsList;
    }

    // Update list (setup)
    // TODO update existing friends (data)
    public void updateList(ArrayList<UserData> invitesList) {
        this.invitesList = invitesList;
        this.reloadList(this.invitesList);
    }

    // Update view
    public void reloadList(ArrayList<UserData> invitesList) {
        this.clear();
        for (UserData invite : invitesList)
            this.add(new InviteObject(invite));
        this.notifyDataSetChanged();
    }

    private void noFriendsWithKeyword() {
        // TODO no friends w/ keyword
    }
}
