package com.pheasant.shutterapp.shutter.ui.features.manage.friends;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.SearchBar;
import com.pheasant.shutterapp.features.shutter.manage.search.StrangersAdapter;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-07.
 */

@Deprecated
public class UsersListManager implements SearchBar.SearchListener, FriendsListListener {

//        this.listManager = new UsersListManager();
//        this.listManager.setup(view, this.getContext(), this.getArguments());
//        this.searchBar.setSearchListener(this.listManager);

    private ListView usersList;
    private FriendsTmpAdapter friendsAdapter;
    private StrangersAdapter strangersAdapter;

    public void setup(View view, Context context, Bundle bundle) {
        this.usersList = (ListView) view.findViewById(R.id.friends_list);
        this.friendsAdapter = new FriendsTmpAdapter(context);
        this.strangersAdapter = new StrangersAdapter(context, bundle);
    }

    @Override
    public void onType(String keyword) {
        this.friendsAdapter.setFilter(keyword);
        this.strangersAdapter.searchUser(keyword);
    }

    @Override
    public void onFriendsListDownloaded(ArrayList<FriendData> friendsList) {
        this.friendsAdapter.updateList(friendsList);
    }

    public void attachFriends() {
        this.usersList.setAdapter(this.friendsAdapter);
    }

    public void attachStrangers() {
        this.usersList.setAdapter(this.strangersAdapter);
    }

}
