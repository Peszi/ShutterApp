package com.pheasant.shutterapp.ui.features.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.data.UserData;
import com.pheasant.shutterapp.ui.features.manage.object.InviteObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class InvitesAdapter extends ArrayAdapter<InviteObject> implements InviteObject.InviteAcceptBtnListener {

    private ArrayList<UserData> invitesList;

    private LayoutInflater layoutInflater;

    private InviteObject.InviteAcceptBtnListener acceptBtnListener;

    public InvitesAdapter(Context context) {
        super(context, R.layout.layout_recipient);
        this.layoutInflater = LayoutInflater.from(context);
        this.invitesList = new ArrayList<>();
    }

    public void setInviteBtnListener(InviteObject.InviteAcceptBtnListener acceptBtnListener) {
        this.acceptBtnListener = acceptBtnListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View baseView = InviteObject.getView(this.getContext(), this.layoutInflater, convertView, parent);
        this.getItem(position).setupView(baseView);
        this.getItem(position).setObjectListener(this);
        return baseView;
    }

    // Filtering list
    public void setFilter(String keyword) {
        if (!keyword.isEmpty()) {
            final ArrayList<UserData> filteredInvitesList = this.getInvitesListWithKeyword(keyword);
            this.reloadList(filteredInvitesList);
            if (filteredInvitesList.size() == 0)
                this.noFriendsWithKeyword();
        } else {
            this.reloadList(this.invitesList);
        }
    }

    private ArrayList<UserData> getInvitesListWithKeyword(String keyword) {
        ArrayList<UserData> filteredInvitesList = new ArrayList<>();
        for (UserData user : this.invitesList)
            if (user.getName().contains(keyword))
                filteredInvitesList.add(user);
        return filteredInvitesList;
    }

    // Update list (setup)
    public void updateList(ArrayList<UserData> invitesList) {
        this.invitesList = invitesList;
        this.reloadList(this.invitesList);
    }

    // Update view
    public void reloadList(ArrayList<UserData> invitesList) {
        this.clear();
        for (UserData invite : invitesList)
            this.add(new InviteObject(invite));
    }

    private void noFriendsWithKeyword() {
        // TODO no friends w/ keyword
    }

    @Override
    public void onInviteAcceptEvent(int userId) {
        if (this.acceptBtnListener != null)
            this.acceptBtnListener.onInviteAcceptEvent(userId);
    }

    @Override
    public void onInviteRejectEvent(int userId) {
        if (this.acceptBtnListener != null)
            this.acceptBtnListener.onInviteRejectEvent(userId);
    }
}
