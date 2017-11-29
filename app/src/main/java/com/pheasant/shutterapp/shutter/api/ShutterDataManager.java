package com.pheasant.shutterapp.shutter.api;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pheasant.shutterapp.shutter.api.data.FriendData;
import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.shutter.api.container.FriendsListContainer;
import com.pheasant.shutterapp.shutter.api.container.PhotoUploadContainer;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.api.requester.InvitesListRequester;
import com.pheasant.shutterapp.shutter.api.requester.SearchListRequester;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterDataManager implements ShutterApiInterface {

    private SearchListRequester searchListRequester;
    private InvitesListRequester invitesListRequester;

    private FriendsListContainer friendsListContainer;
    private PhotoUploadContainer photoUploadContainer;

    public ShutterDataManager(String apiKey) {
        this.searchListRequester = new SearchListRequester(apiKey);
        this.invitesListRequester = new InvitesListRequester(apiKey);
        this.friendsListContainer = new FriendsListContainer(apiKey);
        this.photoUploadContainer = new PhotoUploadContainer(apiKey);
    }

    @Override
    public void registerFriendsListListener(FriendsListListener listListener) {
        this.friendsListContainer.registerFriendsListener(listListener);
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
    public void downloadFriends() {
        this.friendsListContainer.downloadFriendsList();
    }

    @Override
    public void downloadInvites() {
        this.invitesListRequester.downloadList();
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
    public ArrayList<UserData> getInvites() {
        return this.invitesListRequester.getInvitesList();
    }

}
