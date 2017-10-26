package com.example.david.thumbsplit.model;

import org.json.JSONObject;

/**
 * Created by David on 9/29/2017.
 */

public interface VideoListListener {
    public void recivedVideoItem(VideosListModel videosListModel, JSONObject object);

}
