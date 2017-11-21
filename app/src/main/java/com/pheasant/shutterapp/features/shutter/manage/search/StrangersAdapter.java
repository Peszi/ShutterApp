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
import com.pheasant.shutterapp.features.shutter.manage.friends.InviteRequestButton;
import com.pheasant.shutterapp.features.shutter.manage.friends.PageController;
import com.pheasant.shutterapp.features.shutter.manage.friends.ReloadController;
import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.network.request.friends.UserSearchRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-06-09.
 */

public class StrangersAdapter extends ArrayAdapter<StrangerData> implements View.OnClickListener, RequestResultListener, ReloadController {

    private UserSearchRequest userSearchRequest;
    private LayoutInflater layoutInflater;
    private PageController pageController;
    private String apiKey;

    public StrangersAdapter(Context context, Bundle bundle) {
        super(context, R.layout.layout_recipient);
//        this.apiKey = bundle.getString(IntentKey.USER_API_KEY);
        this.layoutInflater = LayoutInflater.from(context);
        this.userSearchRequest = new UserSearchRequest(this.apiKey);
        this.userSearchRequest.setOnRequestResultListener(this);
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
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
            view = this.layoutInflater.inflate(R.layout.layout_stranger, parent, false);
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_invite_btn, view.findViewById(R.id.friend_invite_btn));
        }
        Util.setupFont(this.getContext(), view, Util.FONT_PATH_LIGHT);
        return view;
    }

    private void setupView(View view, int position) {
        StrangerData userData = this.getItem(position);
        if (userData != null) {
            ImageView avatar = (ImageView) view.getTag(R.id.friend_avatar);
            TextView name = (TextView) view.getTag(R.id.friend_name);
            InviteRequestButton inviteBtn = (InviteRequestButton) view.getTag(R.id.friend_invite_btn);
            avatar.setImageResource(R.drawable.avatar_default);
            name.setText(userData.getName());
            inviteBtn.setup(this, this.apiKey, userData.getId());
            inviteBtn.setState(userData.getInvite());
        }
    }

    public void searchUser(String keyword) {
        this.userSearchRequest.cancel();
        if (!keyword.isEmpty()) {
            this.userSearchRequest.setKeyword(keyword);
            this.userSearchRequest.execute();
        } else {
            this.clear();
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
//        if (this.pageController != null)
//            this.pageController.setPageTo(2);
    }

    @Override
    public void onResult(int resultCode) {
        this.clear();
        if (resultCode == Request.RESULT_OK)
            this.addAll(this.userSearchRequest.getStrangersList());
        this.notifyDataSetChanged();
    }

    @Override
    public void onReload() {
        this.userSearchRequest.execute();
    }
}