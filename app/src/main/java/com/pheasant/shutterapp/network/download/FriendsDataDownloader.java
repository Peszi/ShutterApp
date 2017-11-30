package com.pheasant.shutterapp.network.download;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;

import com.pheasant.shutterapp.shutter.api.data.UserData;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shutter.api.request.FriendsListRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-07.
 */

public class FriendsDataDownloader implements RequestResultListener, Runnable {

    private List<UserData> userDataList;
    private FriendsListRequest friendsListRequest;
    //private OnFriendsListListener onFriendsListListener;
    private Context context;

    public FriendsDataDownloader(Context context, String apiKey) {
        this.userDataList = new ArrayList<>();
        this.context = context;
      //  this.friendsListRequest = new FriendsListRequest(apiKey);
       // this.friendsListRequest.setOnRequestResultListener(this);
    }

    public void setOnFriendsListListener(OnFriendsListListener onFriendsListListener) {
       // this.onFriendsListListener = onFriendsListListener;
    }

    public void download() {
        //this.friendsListRequest.execute();
    }

    @Override
    public void run() {
        this.download();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            for (UserData newUserData : this.friendsListRequest.getFriendsList()) {
                UserData userData = this.getFriendData(newUserData);
                if (userData != null) {
                    userData.setName(newUserData.getName());
                    userData.setAvatar(newUserData.getAvatar());
                } else {
                    this.userDataList.add(newUserData);
                }
            }
//            if (this.onFriendsListListener != null)
//                this.onFriendsListListener.onFriendsDownloaded(this.userDataList);
        } else {
            Snackbar.make(((Activity) this.context).getWindow().getDecorView(), "server connection error", Snackbar.LENGTH_LONG).show();
            new Handler().postDelayed(this, UserDataDownloader.RETRY_INTERVAL);
        }
    }

    private UserData getFriendData(UserData newUserData) {
        for (UserData userData : this.userDataList)
            if (userData.getId() == newUserData.getId())
                return userData;
        return null;
    }

    public UserData getFriendDataById(int id) {
        UserData userData = new UserData();
        userData.setId(id);
        return this.getFriendData(userData);
    }

    public List<UserData> getUserDataList() {
        return this.userDataList;
    }

    public Context getContext() {
        return this.context;
    }

    public interface OnFriendsListListener {
        void onFriendsDownloaded(List<UserData> friendsDataList);
    }
}
