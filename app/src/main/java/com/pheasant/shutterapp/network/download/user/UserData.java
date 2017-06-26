package com.pheasant.shutterapp.network.download.user;

import java.io.Serializable;

/**
 * Created by Peszi on 2017-05-18.
 */

public class UserData implements Serializable {

    private String apiKey;

    private int avatar;
    private String name;
    private String email;
    private String created;

    public UserData(int avatar, String name, String email, String created) {
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.created = created;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public int getAvatar() {
        return this.avatar;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCreated() {
        return this.created;
    }
}
