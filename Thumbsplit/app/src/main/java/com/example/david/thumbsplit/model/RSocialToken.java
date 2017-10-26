package com.example.david.thumbsplit.model;

/**
 * Created by Emanuela on 10/24/2017.
 */

public class RSocialToken {
    private String general;
    private String secret;

    public RSocialToken() {
    }

    public RSocialToken(String general) {
        this.general = general;
    }

    public RSocialToken(String general, String secret) {
        this.general = general;
        this.secret = secret;
    }


    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
