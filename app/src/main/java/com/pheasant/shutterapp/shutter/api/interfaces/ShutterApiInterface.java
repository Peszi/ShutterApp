package com.pheasant.shutterapp.shutter.api.interfaces;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.data.FriendData;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotosListListener;
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
    void setPhotoDownloadListener(PhotoDownloadListener downloadListener);
    void registerFriendsListListener(FriendsListListener listListener);
    void registerFriendsPhotosListListener(PhotosListListener listListener);
    // Data request
    void searchUsers(String keyword);
    void reloadSearchResults();
    void downloadFriends();
    void downloadFriendsPhotos();
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
}
