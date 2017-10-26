package com.example.david.thumbsplit.model;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Emanuela on 10/16/2017.
 */

public interface CommentsListListener {
    public void recivedCommentsList(List<CommentsModelList> commentsModelLists, JSONObject object);
}
