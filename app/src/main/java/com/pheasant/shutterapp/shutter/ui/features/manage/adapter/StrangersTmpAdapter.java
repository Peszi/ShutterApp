package com.pheasant.shutterapp.shutter.ui.features.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.network.request.friends.UserSearchRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.StrangerObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-20.
 */

public class StrangersTmpAdapter extends ArrayAdapter<StrangerObject> {

    private LayoutInflater layoutInflater;

    public StrangersTmpAdapter(Context context) {
        super(context, R.layout.layout_recipient);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View baseView = StrangerObject.getView(this.getContext(), this.layoutInflater, convertView, parent);
        this.getItem(position).setupView(baseView);
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
}
