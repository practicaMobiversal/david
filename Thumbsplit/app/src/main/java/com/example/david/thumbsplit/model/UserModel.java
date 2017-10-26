package com.example.david.thumbsplit.model;

/**
 * Created by David on 9/28/2017.
 */

public class UserModel {
    public UserModel(){}

    public UserModel(String token, String username, String profileImg) {
        this.token = token;
        this.username = username;
        this.profileImg = profileImg;
    }

    private String token;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    private String username;
    private String name;
    private String profileImg;

}
