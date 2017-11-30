package com.pheasant.shutterapp.shutter.api.interfaces;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.data.FriendData;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsPhotosListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-11-06.
 */

public interface ShutterApiInterface {
    // Listeners
    void setSearchListListener(SearchListListener listListener);
    void setInvitesListListener(InvitesListListener listListener);
    void setPhotoUploadListener(PhotoUploadListener uploadListener);
    void registerFriendsListListener(FriendsListListener listListener);
    void registerFriendsPhotosListListener(FriendsPhotosListListener listListener);
    // Data request
    void searchUsers(String keyword);
    void reloadSearchResults();
    void downloadFriends();
    void downloadFriendsPhotos();
    void downloadInvites();
    // Data Upload
    void uploadPhoto(Bitmap bitmap, List<Integer> recipientsList);
    void reUploadPhotos();
    // Getters
    ArrayList<FriendData> getFriends();
    ArrayList<PhotoData> getFriendsPhotos();
    ArrayList<UserData> getInvites();
}
