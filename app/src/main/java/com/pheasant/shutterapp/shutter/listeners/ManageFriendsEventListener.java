package com.pheasant.shutterapp.shutter.listeners;

/**
 * Created by Peszi on 2017-11-21.
 */

public interface ManageFriendsEventListener {
    void onPageShow();
    void onTabSelected(int index);
    void onKeywordChange(String keyword);
    void onRefreshButton();
    void onFriendRemoveEvent(int userId);
    void onInviteEvent(int userId);
    void onInviteDeleteEvent(int userId);
}
