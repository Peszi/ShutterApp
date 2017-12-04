package com.pheasant.shutterapp.api;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.api.data.FriendData;
import com.pheasant.shutterapp.api.data.PhotoData;
import com.pheasant.shutterapp.api.data.UserData;
import com.pheasant.shutterapp.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.api.listeners.PhotosListListener;
import com.pheasant.shutterapp.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.api.listeners.SearchListListener;

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
    void setPhotoDownloadListener(PhotoDownloadListener downloadListener);
    void registerFriendsListListener(FriendsListListener listListener);
    void registerFriendsPhotosListListener(PhotosListListener listListener);
    // Data request
    void searchUsers(String keyword);
    void reloadSearchResults();
    void downloadFriends();
    void downloadPhotos();
    void downloadInvites();
    void getThumbnail(int photoId);
    void getPhoto(int photoId);
    // Data Upload
    void uploadPhoto(Bitmap bitmap, List<Integer> recipientsList);
    void reUploadPhotos();
    // Getters
    ArrayList<FriendData> getFriends();
    ArrayList<PhotoData> getFriendsPhotos();
    ArrayList<UserData> getInvites();
    String getApiKey();
}
