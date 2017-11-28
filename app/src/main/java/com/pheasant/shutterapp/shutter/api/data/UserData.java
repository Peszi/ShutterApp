package com.pheasant.shutterapp.shutter.api.data;

/**
 * Created by Peszi on 2017-05-08.
 */

public class UserData {

    private int userId;
    private String userName;
    private int userAvatar;

    public void setId(int userId) {
        this.userId = userId;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public void setAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getId() {
        return this.userId;
    }

    public String getName() {
        return this.userName;
    }

    public int getAvatar() {
        return this.userAvatar;
    }

    public void update(UserData userData) {
        this.userId = userData.userId;
        this.userName = userData.userName;
        this.userAvatar = userData.userAvatar;
    }

}
