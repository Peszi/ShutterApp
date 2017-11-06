package com.pheasant.shutterapp.features.shutter.manage.friends;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.search.StrangersAdapter;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-06-06.
 */

public class FriendsFragment extends Fragment implements View.OnClickListener, SearchBar.SearchListener, TabLayout.OnTabSelectedListener {

    private SearchBar searchBar;
    private TabLayout tabLayout;
    private ListView usersList;
    private FriendsAdapter friendsAdapter;
    private StrangersAdapter strangersAdapter;

    private FloatingActionButton refreshButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_friends_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.searchBar = new SearchBar(this.getContext(), view.findViewById(R.id.search_bar));
        this.searchBar.setSearchListener(this);
        this.tabLayout = (TabLayout) view.findViewById(R.id.profile_bottom_nav);
        this.tabLayout.setOnTabSelectedListener(this);

        this.refreshButton = (FloatingActionButton) view.findViewById(R.id.friends_refresh);
        this.refreshButton.setOnClickListener(this);

        this.usersList = (ListView) view.findViewById(R.id.friends_list);
        this.friendsAdapter = new FriendsAdapter(this.getContext(), this.getArguments());
        this.strangersAdapter = new StrangersAdapter(this.getContext(), this.getArguments());
        this.setToListToFriends();
        return view;
    }

    public void setPageController(PageController pageController) {
        this.strangersAdapter.setPageController(pageController);
    }

    @Override
    public void onClick(View v) {
        this.friendsAdapter.download();
    }

//    @Override
//    public void onShow() {
//        this.searchBar.clearKeyword();
//        this.friendsAdapter.download();
//        this.selectTab(0);
//    }

    private void selectTab(int position) {
        this.tabLayout.getTabAt(position).select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch(tab.getPosition()) {
            case 0: this.setToListToFriends(); break;
            case 1: this.setToListToStrangers(); break;
        }
    }

    private void setToListToFriends() {
        this.searchBar.clearKeyword();
        this.refreshButton.show();
        this.searchBar.setIconToFriends();
        this.usersList.setAdapter(this.friendsAdapter);
        this.friendsAdapter.download();
    }

    private void setToListToStrangers() {
        this.refreshButton.hide();
        this.searchBar.setIconToSearch();
        this.usersList.setAdapter(this.strangersAdapter);
    }

    @Override
    public void onType(String keyword) {
        this.friendsAdapter.setFilter(keyword);
        this.strangersAdapter.searchUser(keyword);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
