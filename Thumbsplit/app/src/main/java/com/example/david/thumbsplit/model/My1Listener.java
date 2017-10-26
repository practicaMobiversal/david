package com.example.david.thumbsplit.model;

import com.example.david.thumbsplit.model.MyListener;

/**
 * Created by David on 9/28/2017.
 */

public interface My1Listener extends MyListener {

    public void recivedJWT(String token);
    public void recivedCodeFromServer(int code);
}
