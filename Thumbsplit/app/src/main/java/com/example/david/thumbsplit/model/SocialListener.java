package com.example.david.thumbsplit.model;

/**
 * Created by Emanuela on 10/24/2017.
 */

public interface SocialListener {

    void onLoggedUser(String jwtToken);
    void onRegisteredUser(String jwtToken);
    void trySocialRegister();
}
