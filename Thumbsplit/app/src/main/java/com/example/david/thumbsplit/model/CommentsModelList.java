package com.example.david.thumbsplit.model;

/**
 * Created by Emanuela on 10/16/2017.
 */

public class CommentsModelList {

    private String text;
    private int like_status,dislike,like;
    private long createdAt;
    private int commentId;

    public CommentsModelList(String text, int like_status, int dislike, int like, long createdAt, UserModel user,int commentId) {
        this.text = text;
        this.like_status = like_status;
        this.dislike = dislike;
        this.like = like;
        this.createdAt = createdAt;
        this.user = user;
        this.commentId=commentId;
    }

    private UserModel user;

    public int getCommentId(){
        return commentId;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
