package com.pheasant.shutterapp.shutter.ui.features;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.data.FriendData;
import com.pheasant.shutterapp.shutter.api.data.StrangerData;
import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.ui.interfaces.ManageFriendsView;
import com.pheasant.shutterapp.shutter.presenter.ManageFriendsPresenter;
import com.pheasant.shutterapp.shutter.ui.features.manage.SearchBar;
import com.pheasant.shutterapp.shutter.ui.features.manage.adapter.FriendsAdapter;
import com.pheasant.shutterapp.shutter.ui.features.manage.adapter.InvitesAdapter;
import com.pheasant.shutterapp.shutter.ui.features.manage.adapter.StrangersAdapter;
import com.pheasant.shutterapp.shutter.ui.shared.NotifiableFragment;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-07.
 */
// TODO "u sure?" dialog boxes
public class ManageFragment extends NotifiableFragment implements TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener, SearchBar.SearchListener, ManageFriendsView {

    private final int ADAPTERS_COUNT = 3;

    private ListView usersList;
    private SearchBar searchBar;
    private TabLayout tabLayout;
    private TextView infoMessage;
    private SwipeRefreshLayout refreshLayout;

    private ListAdapter[] listAdapters;

    private ManageFriendsPresenter friendsPresenter;

    public ManageFragment(String apiKey) {
        this.friendsPresenter = new ManageFriendsPresenter(apiKey);
        this.friendsPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_friends_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);
        this.setupUI(view);
        this.setupAdapters();
        return view;
    }

    private void setupUI(View view) {
        this.searchBar = new SearchBar(this.getContext(), view.findViewById(R.id.search_bar));
        this.searchBar.setSearchListener(this);
        this.tabLayout = (TabLayout) view.findViewById(R.id.profile_bottom_nav);
        this.tabLayout.setOnTabSelectedListener(this);
        this.usersList = (ListView) view.findViewById(R.id.friends_list);
        this.infoMessage = (TextView) view.findViewById(R.id.friends_result);
        this.refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.friends_list_refresh);
        this.refreshLayout.setOnRefreshListener(this);
    }

    public void setupAdapters() {
        this.listAdapters = new ListAdapter[this.ADAPTERS_COUNT];
        this.listAdapters[this.friendsPresenter.FRIENDS_ADAPTER_IDX] = new FriendsAdapter(this.getContext());
        this.listAdapters[this.friendsPresenter.INVITES_ADAPTER_IDX] = new InvitesAdapter(this.getContext());
        this.listAdapters[this.friendsPresenter.STRANGERS_ADAPTER_IDX] = new StrangersAdapter(this.getContext());
        this.getFriendsAdapter().setRemoveBtnListener(this.friendsPresenter);
        this.getInvitesAdapter().setInviteBtnListener(this.friendsPresenter);
        this.getStrangersAdapter().setInviteBtnListener(this.friendsPresenter);
    }

    public void setShutterDataManager(ShutterApiInterface friendsInterface) {
        this.friendsPresenter.setShutterApiInterface(friendsInterface);
    }

    // UI Events handling

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
    public void onRefresh() {
        this.friendsPresenter.onRefreshEvent();
    }

    // View Interface

    @Override
    public void searchClearKeyword() {
        this.searchBar.clearKeyword();
    }

    @Override
    public void searchSetIcon(int index) {
        this.searchBar.setIcon(index);
    }

    @Override
    public void refreshSetRefreshing(boolean show) {
        this.refreshLayout.setRefreshing(show);
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

    private FriendsAdapter getFriendsAdapter() {
        return (FriendsAdapter) this.listAdapters[this.friendsPresenter.FRIENDS_ADAPTER_IDX];
    }

    private StrangersAdapter getStrangersAdapter() {
        return (StrangersAdapter) this.listAdapters[this.friendsPresenter.STRANGERS_ADAPTER_IDX];
    }

    private InvitesAdapter getInvitesAdapter() {
        return (InvitesAdapter) this.listAdapters[this.friendsPresenter.INVITES_ADAPTER_IDX];
    }

}
