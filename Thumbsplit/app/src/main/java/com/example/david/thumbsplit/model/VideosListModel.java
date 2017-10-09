package com.example.david.thumbsplit.model;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by David on 9/29/2017.
 */

public class VideosListModel {

    String videoTitle,thumbnail,thumbnail_image,videoUrl,shareUrl;
    int videoLength;
    int views;
    int likeStatus;
    int likeCount;
    int dislikeCount;
    String description;
    List<UserModel> taggedUsers;
    public List<UserModel> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<UserModel> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateDate() {

        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    long createDate;
    UserModel videoOwner;

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getVideoLenght() {
        return videoLength;
    }

    public void setVideoLenght(int videoLenght) {
        this.videoLength = videoLenght;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public UserModel getVideoOwner() {
        return videoOwner;
    }

    public void setVideoOwner(UserModel videoOwner) {
        this.videoOwner = videoOwner;
    }

    public VideosListModel(String videoTitle, String thumbnail, String thumbnail_image, String videoUrl, String shareUrl, int videoLength, int views, int likeStatus, int likeCount, int dislikeCount, UserModel videoOwner,long createDate,int id) {
        this.videoTitle = videoTitle;
        this.thumbnail = thumbnail;
        this.thumbnail_image = thumbnail_image;
        this.videoUrl = videoUrl;
        this.shareUrl = shareUrl;
        this.videoLength = videoLength;
        this.views = views;
        this.likeStatus = likeStatus;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.videoOwner = videoOwner;
        this.createDate=createDate;
        this.id=id;
    }
    public VideosListModel(String videoTitle, String thumbnail, String thumbnail_image, String videoUrl,
                           String shareUrl, int videoLength, int views, int likeStatus, int likeCount, int dislikeCount,
                           UserModel videoOwner,long createDate,int id,String description, List<UserModel> taggedUsers) {
        this.videoTitle = videoTitle;
        this.thumbnail = thumbnail;
        this.thumbnail_image = thumbnail_image;
        this.videoUrl = videoUrl;
        this.shareUrl = shareUrl;
        this.videoLength = videoLength;
        this.views = views;
        this.likeStatus = likeStatus;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.videoOwner = videoOwner;
        this.createDate=createDate;
        this.id=id;
        this.description=description;
        this.taggedUsers=taggedUsers;
    }
}
