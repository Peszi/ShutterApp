package com.pheasant.shutterapp.features.shutter.camera.recipients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-19.
 */

public class RecipientsAdapter extends ArrayAdapter<UserData> implements View.OnClickListener, CheckButton.ChangeListener {

    private ListView recipientsList;
    private ToggleButton checkAllButton;
    private LayoutInflater layoutInflater;

    public RecipientsAdapter(Context context, ListView recipientsList, ToggleButton checkAllButton) {
        super(context, R.layout.layout_recipient);
        this.layoutInflater = LayoutInflater.from(context);
        this.recipientsList = recipientsList;
        this.recipientsList.setAdapter(this);
        this.checkAllButton = checkAllButton;
        this.checkAllButton.setOnClickListener(this);
    }

    public void prepare(List<UserData> friendsData) {
        this.setCheckedEveryone(false);
        this.setAllCheckButton(false);
        this.clear();
        this.addAll(friendsData);
        this.notifyDataSetChanged();
    }

    public void setCheckedEveryone(boolean check) {
        for (int i = 0; i < this.recipientsList.getCount(); i++) {
            View view = this.recipientsList.getChildAt(i);
            if (view != null) {
                ((ToggleButton) view.findViewById(R.id.recipient_check_btn)).setChecked(check);
                view.findViewById(R.id.recipient_check_btn).callOnClick();
            }
        }
    }

    public boolean allTheSameState(boolean check) {
        for (int i = 0; i < this.recipientsList.getCount(); i++) {
            View view = this.recipientsList.getChildAt(i);
            if (((ToggleButton) view.findViewById(R.id.recipient_check_btn)).isChecked() != check)
                return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        this.setCheckedEveryone(((ToggleButton) v).isChecked());
    }

    @Override
    public void onStateChanged() {
        if (this.allTheSameState(true))
            this.setAllCheckButton(true);
        if (this.allTheSameState(false))
            this.setAllCheckButton(false);
    }

    private void setAllCheckButton(boolean check) {
        this.checkAllButton.setChecked(check);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = this.layoutInflater.inflate(R.layout.layout_recipient, parent, false);
            view.setTag(R.id.recipient_avatar, view.findViewById(R.id.recipient_avatar));
            view.setTag(R.id.recipient_name, view.findViewById(R.id.recipient_name));
            view.setTag(R.id.recipient_check_btn, view.findViewById(R.id.recipient_check_btn));
        }
        Util.setupFont(this.getContext(), view, Util.FONT_PATH_LIGHT);
        UserData userData = this.getItem(position);
        if (userData != null) {
            final ImageView recipientAvatar = (ImageView) view.getTag(R.id.recipient_avatar);
            final TextView recipientName = (TextView) view.getTag(R.id.recipient_name);
            recipientAvatar.setImageResource(Avatar.getAvatar(userData.getAvatar()));
            recipientName.setText(userData.getName());
            ((CheckButton) view.getTag(R.id.recipient_check_btn)).setChangeListener(this);
        }
        return view;
    }

    public List<Integer> getCheckedIds() {
        List<Integer> checkedList = new ArrayList<>();
        for (int i = 0; i < this.recipientsList.getCount(); i++) {
            View view = this.recipientsList.getChildAt(i);
            if (view != null && ((ToggleButton) view.findViewById(R.id.recipient_check_btn)).isChecked())
                checkedList.add(this.getItem(i).getId());
        }
        return checkedList;
    }
}
