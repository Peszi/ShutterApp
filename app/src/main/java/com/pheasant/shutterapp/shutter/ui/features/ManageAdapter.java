package com.pheasant.shutterapp.shutter.ui.features;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.interfaces.ManageFriendsView;
import com.pheasant.shutterapp.shutter.presenter.ManageFriendsPresenter;
import com.pheasant.shutterapp.shutter.ui.features.manage.SearchBar;
import com.pheasant.shutterapp.shutter.ui.features.manage.adapter.FriendsTmpAdapter;
import com.pheasant.shutterapp.shutter.ui.features.manage.adapter.InvitesTmpAdapter;
import com.pheasant.shutterapp.shutter.ui.features.manage.adapter.StrangersTmpAdapter;
import com.pheasant.shutterapp.shutter.ui.util.NotifiableFragment;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-07.
 */

public class ManageAdapter extends NotifiableFragment implements TabLayout.OnTabSelectedListener, View.OnClickListener, SearchBar.SearchListener, ManageFriendsView {

    // TODO new invite button
    // TODO refresh gesture
    // TODO "u sure?" dialog boxes

    private final int ADAPTERS_COUNT = 3;

    private ListView usersList;
    private SearchBar searchBar;
    private TabLayout tabLayout;
    private TextView infoMessage;
    private FloatingActionButton refreshButton;

    private ListAdapter[] listAdapters;

    private ManageFriendsPresenter friendsPresenter;

    public ManageAdapter(String apiKey) {
        super();
        this.friendsPresenter = new ManageFriendsPresenter(apiKey);
        this.friendsPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_friends_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.searchBar = new SearchBar(this.getContext(), view.findViewById(R.id.search_bar));
        this.searchBar.setSearchListener(this);
        this.tabLayout = (TabLayout) view.findViewById(R.id.profile_bottom_nav);
        this.tabLayout.setOnTabSelectedListener(this);
        this.infoMessage = (TextView) view.findViewById(R.id.friends_result);
        this.refreshButton = (FloatingActionButton) view.findViewById(R.id.friends_refresh);
        this.refreshButton.setOnClickListener(this);

        this.usersList = (ListView) view.findViewById(R.id.friends_list);
        this.listAdapters = new ListAdapter[this.ADAPTERS_COUNT];
        this.listAdapters[this.friendsPresenter.FRIENDS_ADAPTER_IDX] = new FriendsTmpAdapter(this.getContext());
        this.listAdapters[this.friendsPresenter.INVITES_ADAPTER_IDX] = new InvitesTmpAdapter(this.getContext());
        this.listAdapters[this.friendsPresenter.STRANGERS_ADAPTER_IDX] = new StrangersTmpAdapter(this.getContext());
        this.getFriendsAdapter().setRemoveBtnListener(this.friendsPresenter);
        this.getInvitesAdapter().setInviteBtnListener(this.friendsPresenter);
        this.getStrangersAdapter().setInviteBtnListener(this.friendsPresenter);
        return view;
    }

    public void setFriendsInterface(ShutterApiInterface friendsInterface) {
        this.friendsPresenter.setShutterApiInterface(friendsInterface);
    }

    // Events handling

    @Override
    public void onShow() {
        this.friendsPresenter.onPageShow();
    }

    @Override
    public void onType(String keyword) {
        this.friendsPresenter.onKeywordChange(keyword);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.friendsPresenter.onTabSelected(tab.getPosition());
    }

    @Override
    public void onClick(View v) {
        this.friendsPresenter.onRefreshButtonEvent();
    }

    // Interface

    @Override
    public void searchClearKeyword() {
        this.searchBar.clearKeyword();
    }

    @Override
    public void searchSetIcon(int index) {
        this.searchBar.setIcon(index);
    }

    @Override
    public void refreshShowButton(boolean show) {
        if (show) { this.refreshButton.show(); }
        else { this.refreshButton.hide(); }
    }

    @Override
    public void listSetAdapter(int index) {
        if (index < this.listAdapters.length)
            this.usersList.setAdapter(this.listAdapters[index]);
    }

    @Override
    public void tabForceSelect(int index) {
        this.tabLayout.getTabAt(index).select();
    }

    @Override
    public void friendsSetKeywordFilter(String keyword) {
        this.getFriendsAdapter().setFilter(keyword);
    }

    @Override
    public void friendsListUpdate(ArrayList<FriendData> friendsList) {
        this.getFriendsAdapter().updateList(friendsList);
    }

    @Override
    public void strangersListClear() {
        this.getStrangersAdapter().clearList();
    }

    @Override
    public void strangersListUpdate(ArrayList<StrangerData> strangersList) {
        this.getStrangersAdapter().updateList(strangersList);
    }

    @Override
    public void invitesSetKeywordFilter(String keyword) {
        this.getInvitesAdapter().setFilter(keyword);
    }

    @Override
    public void invitesListUpdate(ArrayList<UserData> invitesList) {
        this.getInvitesAdapter().updateList(invitesList);
    }

    @Override
    public void showInfoMessage(String message) {
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showBarMessage(CharSequence message) {
        this.infoMessage.setText(message);
        this.infoMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBar() {
        this.infoMessage.setVisibility(View.GONE);
    }

    // Not used
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }
    // Not used
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    // Getters

    private FriendsTmpAdapter getFriendsAdapter() {
        return (FriendsTmpAdapter) this.listAdapters[this.friendsPresenter.FRIENDS_ADAPTER_IDX];
    }

    private StrangersTmpAdapter getStrangersAdapter() {
        return (StrangersTmpAdapter) this.listAdapters[this.friendsPresenter.STRANGERS_ADAPTER_IDX];
    }

    private InvitesTmpAdapter getInvitesAdapter() {
        return (InvitesTmpAdapter) this.listAdapters[this.friendsPresenter.INVITES_ADAPTER_IDX];
    }
}
