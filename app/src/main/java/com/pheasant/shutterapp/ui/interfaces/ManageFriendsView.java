package com.pheasant.shutterapp.ui.interfaces;

import com.pheasant.shutterapp.api.data.FriendData;
import com.pheasant.shutterapp.api.data.StrangerData;
import com.pheasant.shutterapp.api.data.UserData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public interface ManageFriendsView {
    void searchClearKeyword();
    void searchSetIcon(int index);

    void refreshSetRefreshing(boolean show);
    void listSetAdapter(int index);
    void tabForceSelect(int index);

    void friendsSetKeywordFilter(String keyword);
    void friendsListUpdate(ArrayList<FriendData> friendsList);

    void strangersListClear();
    void strangersListUpdate(ArrayList<StrangerData> friendsList);

    void invitesSetKeywordFilter(String keyword);
    void invitesListUpdate(ArrayList<UserData> invitesList);

    void showInfoMessage(String message);
    void showBarMessage(CharSequence message);
    void hideBar();
}
