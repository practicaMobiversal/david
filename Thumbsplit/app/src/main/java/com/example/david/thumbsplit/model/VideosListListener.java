package com.example.david.thumbsplit.model;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by David on 10/11/2017.
 */

public interface VideosListListener {
    public void recivedVideosList(List<VideosListModel> mList, JSONObject jsonObject);
}
