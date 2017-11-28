package com.pheasant.shutterapp.shutter.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.shutter.api.data.FriendData;
import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-28.
 */

public class RecipientObject implements CompoundButton.OnCheckedChangeListener {

    private ToggleButton checkButton;

    private final UserData userData;

    private RecipientCheckBtnListener checkListener;

    public RecipientObject(UserData userData) {
        this.userData = userData;
    }

    public void setObjectListener(RecipientCheckBtnListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setCheckBtnState(boolean check) {
        if (this.checkButton != null) {
            this.checkButton.setChecked(check);
            this.checkButton.callOnClick();
        }
    }

    public void setupView(View view) {
        if (userData != null) {
            final ImageView recipientAvatar = (ImageView) view.getTag(R.id.recipient_avatar);
            final TextView recipientName = (TextView) view.getTag(R.id.recipient_name);
            this.checkButton = (ToggleButton) view.getTag(R.id.recipient_check);
            recipientAvatar.setImageResource(Avatar.getAvatar(userData.getAvatar()));
            recipientName.setText(userData.getName());
            this.checkButton.setOnCheckedChangeListener(this);
        }
    }

    public static View getView(Context context, LayoutInflater layoutInflater, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.layout_recipient, parent, false);
            view.setTag(R.id.recipient_avatar, view.findViewById(R.id.recipient_avatar));
            view.setTag(R.id.recipient_name, view.findViewById(R.id.recipient_name));
            view.setTag(R.id.recipient_check, view.findViewById(R.id.recipient_check));
        }
        Util.setupFont(context, view, Util.FONT_PATH_LIGHT);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (this.checkListener != null)
            this.checkListener.onCheckBtnStateChange();
    }

    public boolean isBtnChecked() {
        return this.checkButton.isChecked();
    }

    public int getUserId(){ return this.userData.getId(); }

    public interface RecipientCheckBtnListener {
        void onCheckBtnStateChange();
    }
}
