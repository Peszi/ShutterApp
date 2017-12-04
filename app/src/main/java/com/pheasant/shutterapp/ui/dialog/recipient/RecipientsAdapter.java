package com.pheasant.shutterapp.ui.dialog.recipient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.data.FriendData;
import com.pheasant.shutterapp.api.data.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-19.
 */

public class RecipientsAdapter extends ArrayAdapter<RecipientObject> implements RecipientObject.RecipientCheckBtnListener, View.OnClickListener {

    private ToggleButton checkAllButton;

    private LayoutInflater layoutInflater;

    public RecipientsAdapter(Context context) {
        super(context, R.layout.layout_recipient);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setCheckAllButton(ToggleButton checkAllButton) {
        this.checkAllButton = checkAllButton;
        this.checkAllButton.setOnClickListener(this);
    }

    public void updateList(List<FriendData> friendsData) {
        this.setCheckedEveryRecipient(false);
        this.setAllCheckButton(false);
        this.clear();
        for (UserData userData : friendsData)
            this.add(new RecipientObject(userData));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View baseView = RecipientObject.getView(this.getContext(), this.layoutInflater, convertView, parent);
        this.getItem(position).setupView(baseView);
        this.getItem(position).setObjectListener(this);
        return baseView;
    }

    @Override
    public void onClick(View v) {
        if (this.checkAllButton != null)
            this.setCheckedEveryRecipient(this.checkAllButton.isChecked());
    }

    @Override
    public void onCheckBtnStateChange() {
        if (this.isAllInState(true))
            this.setAllCheckButton(true);
        if (this.isAllInState(false))
            this.setAllCheckButton(false);
    }

    private void setCheckedEveryRecipient(boolean check) {
        for (int i = 0; i < this.getCount(); i++)
            this.getItem(i).setCheckBtnState(check);
    }

    private boolean isAllInState(boolean check) {
        for (int i = 0; i < this.getCount(); i++)
            if (this.getItem(i).isBtnChecked() != check)
                return false;
        return true;
    }

    private void setAllCheckButton(boolean check) {
        this.checkAllButton.setChecked(check);
    }

    public List<Integer> getCheckedIds() {
        List<Integer> checkedList = new ArrayList<>();
        for (int i = 0; i < this.getCount(); i++)
            if (this.getItem(i).isBtnChecked())
                checkedList.add(this.getItem(i).getUserId());
        return checkedList;
    }
}
