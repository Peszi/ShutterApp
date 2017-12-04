package com.pheasant.shutterapp.api;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.api.container.PhotoManager;
import com.pheasant.shutterapp.api.container.PhotosListContainer;
import com.pheasant.shutterapp.api.data.FriendData;
import com.pheasant.shutterapp.api.data.PhotoData;
import com.pheasant.shutterapp.api.data.UserData;
import com.pheasant.shutterapp.api.container.FriendsListContainer;
import com.pheasant.shutterapp.api.container.PhotoUploadContainer;
import com.pheasant.shutterapp.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.api.listeners.PhotoDownloadListener;
import com.pheasant.shutterapp.api.listeners.PhotosListListener;
import com.pheasant.shutterapp.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.api.listeners.SearchListListener;
import com.pheasant.shutterapp.api.requester.InvitesListRequester;
import com.pheasant.shutterapp.api.requester.SearchListRequester;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterDataManager implements ShutterApiInterface {

    private SearchListRequester searchListRequester;
    private InvitesListRequester invitesListRequester;

    private FriendsListContainer friendsListContainer;
    private PhotosListContainer photosListContainer;
    private PhotoUploadContainer photoUploadContainer;
    private PhotoManager photoManager;

    private String apiKey;

    public ShutterDataManager(Context context, String apiKey) {
        this.apiKey = apiKey;
        this.searchListRequester = new SearchListRequester(apiKey);
        this.invitesListRequester = new InvitesListRequester(apiKey);
        this.friendsListContainer = new FriendsListContainer(apiKey);
        this.photosListContainer = new PhotosListContainer(apiKey);
        this.photoUploadContainer = new PhotoUploadContainer(apiKey);
        this.photoManager = new PhotoManager(context, apiKey);
    }

    @Override
    public void setSearchListListener(SearchListListener listListener) {
        this.searchListRequester.setListListener(listListener);
    }

    @Override
    public void setInvitesListListener(InvitesListListener listListener) {
        this.invitesListRequester.setListListener(listListener);
    }

    @Override
    public void setPhotoUploadListener(PhotoUploadListener uploadListener) {
        this.photoUploadContainer.setUploadListener(uploadListener);
    }

    @Override
    public void setPhotoDownloadListener(PhotoDownloadListener downloadListener) {
        this.photoManager.setDownloadListener(downloadListener);
    }

    @Override
    public void registerFriendsListListener(FriendsListListener listListener) {
        this.friendsListContainer.registerFriendsListener(listListener);
    }

    @Override
    public void registerFriendsPhotosListListener(PhotosListListener listListener) {
        this.photosListContainer.registerFriendsListener(listListener);
    }

    @Override
    public void downloadFriends() {
        this.friendsListContainer.downloadFriendsList();
    }

    @Override
    public void downloadPhotos() {
        this.photosListContainer.downloadPhotosList();
    }

    @Override
    public void downloadInvites() {
        this.invitesListRequester.downloadList();
    }

    @Override
    public void getThumbnail(int photoId) {
        this.photoManager.getThumbnail(photoId);
    }

    @Override
    public void getPhoto(int photoId) {
        this.photoManager.getPhoto(photoId);
    }

    @Override
    public void uploadPhoto(Bitmap bitmap, List<Integer> recipientsList) {
        this.photoUploadContainer.uploadPhoto(bitmap, recipientsList);
    }

    @Override
    public void reUploadPhotos() {
        this.photoUploadContainer.reUpload();
    }

    @Override
    public void searchUsers(String keyword) {
        this.searchListRequester.searchByKeyword(keyword);
    }

    @Override
    public void reloadSearchResults() {
        this.searchListRequester.reloadList();
    }

    @Override
    public ArrayList<FriendData> getFriends() {
        return this.friendsListContainer.getFriendsList();
    }

    @Override
    public ArrayList<PhotoData> getFriendsPhotos() {
        return this.photosListContainer.getPhotosList();
    }

    @Override
    public ArrayList<UserData> getInvites() {
        return this.invitesListRequester.getInvitesList();
    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }

}
