package com.pheasant.shutterapp.shutter.tmp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.FriendsAdapter;
import com.pheasant.shutterapp.features.shutter.manage.friends.SearchBar;
import com.pheasant.shutterapp.features.shutter.manage.search.StrangersAdapter;

/**
 * Created by Peszi on 2017-11-07.
 */

public class UsersListManager implements SearchBar.SearchListener {

    private ListView usersList;

    private FriendsAdapter friendsAdapter;
    private StrangersAdapter strangersAdapter;

    public UsersListManager(View view, Context context, Bundle bundle) {
        this.usersList = (ListView) view.findViewById(R.id.friends_list);
        this.friendsAdapter = new FriendsAdapter(context, bundle);
        this.strangersAdapter = new StrangersAdapter(context, bundle);
    }

    @Override
    public void onType(String keyword) {
        this.friendsAdapter.setFilter(keyword);
        this.strangersAdapter.searchUser(keyword);
    }

    public void attachFriends() {
        this.usersList.setAdapter(this.friendsAdapter);
    }

    public void attachStrangers() {
        this.usersList.setAdapter(this.strangersAdapter);
    }
}
