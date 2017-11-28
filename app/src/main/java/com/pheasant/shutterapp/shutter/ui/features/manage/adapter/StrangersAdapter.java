package com.pheasant.shutterapp.shutter.ui.features.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.data.StrangerData;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.StrangerObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-20.
 */

public class StrangersAdapter extends ArrayAdapter<StrangerObject> implements StrangerObject.InviteCreateBtnListener {

    private LayoutInflater layoutInflater;
    private StrangerObject.InviteCreateBtnListener objectListener;

    public StrangersAdapter(Context context) {
        super(context, R.layout.layout_recipient);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setInviteBtnListener(StrangerObject.InviteCreateBtnListener objectListener) {
        this.objectListener = objectListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View baseView = StrangerObject.getView(this.getContext(), this.layoutInflater, convertView, parent);
        this.getItem(position).setupView(baseView);
        this.getItem(position).setObjectListener(this);
        return baseView;
    }

    public void clearList() {
        this.clear();
        this.notifyDataSetChanged();
    }

    public void updateList(ArrayList<StrangerData> usersList) {
        this.clear();
        for (StrangerData strangerData : usersList)
            this.add(new StrangerObject(strangerData));
        this.notifyDataSetChanged();
    }

    @Override
    public void onInviteCreateEvent(int userId) {
        if (this.objectListener != null)
            this.objectListener.onInviteCreateEvent(userId);
    }

    @Override
    public void onInviteRemoveEvent(int userId) {
        if (this.objectListener != null)
            this.objectListener.onInviteRemoveEvent(userId);
    }
}
