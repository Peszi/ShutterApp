package com.pheasant.shutterapp.shutter.interfaces;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.StrangerData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public interface ManageFriendsView {
    void searchClearKeyword();
    void searchSetIcon(int index);

    void refreshShowButton(boolean show);
    void listSetAdapter(int index);
    void tabForceSelect(int index);

    void friendsSetKeywordFilter(String keyword);
    void friendsListUpdate(ArrayList<FriendData> friendsList);

    void strangersListClear();
    void strangersListUpdate(ArrayList<StrangerData> friendsList);
}
