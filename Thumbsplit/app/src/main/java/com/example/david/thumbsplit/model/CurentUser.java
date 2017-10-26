package com.example.david.thumbsplit.model;

/**
 * Created by Emanuela on 10/24/2017.
 */

public class CurentUser {
String email,userId;
    int type;

    public RSocialToken getToken() {
        return token;
    }

    public void setToken(RSocialToken token) {
        this.token = token;
    }

    RSocialToken token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
