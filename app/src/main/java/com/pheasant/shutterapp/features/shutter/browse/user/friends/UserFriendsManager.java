package com.pheasant.shutterapp.features.shutter.browse.user.friends;

/**
 * Created by Peszi on 2017-06-06.
 */

public class UserFriendsManager {

    private FriendsListDownloader friendsDownloader;

    public UserFriendsManager(String apiKey) {
        this.friendsDownloader = new FriendsListDownloader(apiKey);
    }

    public void setFriendsListener(FriendsListener onFriendsListListener) {
        this.friendsDownloader.setOnFriendsListListener(onFriendsListListener);
    }

    public void reload() {
        this.friendsDownloader.download();
    }
}
