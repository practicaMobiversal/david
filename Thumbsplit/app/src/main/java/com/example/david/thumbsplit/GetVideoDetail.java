package com.example.david.thumbsplit;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.CommentsModelList;
import com.example.david.thumbsplit.model.UserModel;
import com.example.david.thumbsplit.model.VideoListListener;
import com.example.david.thumbsplit.model.VideosListListener;
import com.example.david.thumbsplit.model.VideosListModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.getLong;

/**
 * Created by David on 10/3/2017.
 */

public class GetVideoDetail {
    public int page_size=3;

    String token;
    String details_url="http://52.14.245.160:4096/v1/get-video-detail";
    int videoId;
    private VideoListListener videoListListener;
    String videoTitle,thumbnail,thumbnail_image,videoUrl,shareUrl;
    int videoLength,views,likeStatus,likeCount,dislikeCount,id,commentId;
    long createDate,createdAt;
    UserModel videoOwner;
    VideosListModel videosListModel;
    private JSONObject next_object;
    String description;
    List<UserModel> taggedUsers=new ArrayList<>();
    UserModel videoTagged;

    List<CommentsModelList> commentsModelLists=new ArrayList<>();
    CommentsModelList commentsModel;

    public GetVideoDetail(int videoId,String token,VideoListListener videoListListener){
        this.videoId=videoId;
        this.token=token;
        this.videoListListener=videoListListener;
    }
    private HashMap<String, String> headers = new HashMap<>();

    public StringRequest stringRequest=new StringRequest(Request.Method.POST, details_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject= new JSONObject(response);
                JSONObject video=jsonObject.getJSONObject("data").getJSONObject("video");
                videoTitle=video.getString("title");
                videoUrl=video.getString("url");
                videoLength=video.getInt("video_length");
                thumbnail=video.getString("thumbnail");
                thumbnail_image=video.getString("thumbnail_image");
                views=video.getInt("views");
                likeStatus=video.getInt("like_status");
                likeCount=video.getInt("like_count");
                dislikeCount=video.getInt("dislike_count");
                createDate=video.getLong("created_at");
                id=video.getInt("id");
                description=video.getString("description");
                JSONObject user=video.getJSONObject("user");
                String username=user.getString("username");
                String profileImg=user.getString("profile_image");
                videoOwner=new UserModel("0",username,profileImg);
                JSONArray taggedArray=video.getJSONArray("tagged_users");
                for(int i=0;i<taggedArray.length();i++){
                    JSONObject tagged=taggedArray.getJSONObject(i);
                    String tagguser=tagged.getString("username");
                    String taggimg=tagged.getString("profile_image");
                    videoTagged=new UserModel("0",tagguser,taggimg);
                    taggedUsers.add(videoTagged);
                }
                JSONArray commentsArray =video.getJSONArray("first_page_comments");
                for(int i=0;i<commentsArray.length();i++)
                {
                    JSONObject comment=commentsArray.getJSONObject(i);
                    String text=comment.getString("text");
                    commentId=comment.getInt("id");
                    int like_stats=comment.getInt("like_status");
                    int like=comment.getInt("like_count");
                    int dislike=comment.getInt("dislike_count");
                     createdAt=comment.getLong("created_at");
                    JSONObject commUser=comment.getJSONObject("user");
                    String comUserName=commUser.getString("username");
                    String comProfileImage= commUser.getString("profile_image");
                    UserModel commentOwner=new UserModel("0",comUserName,comProfileImage);
                    commentsModel=new CommentsModelList(text,like_stats,dislike,like,createdAt,commentOwner,commentId);
                    commentsModelLists.add(commentsModel);

                    next_object=comment.getJSONObject("last_element");
                }


                videosListModel=new VideosListModel(videoTitle,thumbnail,thumbnail_image,
                        videoUrl,shareUrl,videoLength,views,likeStatus,likeCount,dislikeCount,videoOwner,createDate,id,description,taggedUsers,commentsModelLists);

                //saving model
                videoListListener.recivedVideoItem(videosListModel,next_object);




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    },new Response.ErrorListener(){public void onErrorResponse(VolleyError error) {

    }}) {
        //corp functie

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            if (headers == null) headers = new HashMap<>();
            if (!TextUtils.isEmpty(token)) {
                headers.put("Authorization", String.format("Bearer %s", token));
            }
            return headers;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map <String,String> params=new HashMap<>();
            params.put("video_id", String.valueOf(videoId));
            params.put("page_size", String.valueOf(page_size));
            return params;
        }


    };



}
