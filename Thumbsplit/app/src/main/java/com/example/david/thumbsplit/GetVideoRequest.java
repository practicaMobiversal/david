package com.example.david.thumbsplit;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import static java.security.AccessController.getContext;

/**
 * Created by David on 9/28/2017.
 */

public class GetVideoRequest {

    String token;
    String videos_url="http://52.14.245.160:4096/v1/get-videos";
    private VideosListListener videosListListener;
    String videoTitle,thumbnail,thumbnail_image,videoUrl,shareUrl;
    int videoLength,views,likeStatus,likeCount,dislikeCount,id;
    long createDate;
    UserModel videoOwner;
    VideosListModel videosListModel;
    private List<VideosListModel> mList=new ArrayList<>();
   int page_size=3;
    private JSONObject last_object,next_object;

    private HashMap<String, String> headers = new HashMap<>();

    public GetVideoRequest(String jwt,JSONObject last_obj,VideosListListener videosListListener) {
        this.token = jwt;
        this.videosListListener=videosListListener;
        this.last_object=last_obj;
    }


    public StringRequest stringRequest=new StringRequest(Request.Method.POST, videos_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray videosArray=jsonObject.getJSONObject("data").getJSONArray("videos");
                for(int i = 0;i < videosArray.length(); i++){
                    JSONObject item = videosArray.getJSONObject(i);
                     videoTitle = item.getString("title");
                    thumbnail=item.getString("thumbnail");
                    thumbnail_image=item.getString("thumbnail_image");
                    videoUrl=item.getString("url");
                    videoLength=item.getInt("video_length");
                    views=item.getInt("views");
                    likeStatus=item.getInt("like_status");
                    likeCount=item.getInt("like_count");
                    dislikeCount=item.getInt("dislike_count");
                    createDate=item.getLong("created_at");
                    id=item.getInt("id");
                   JSONObject user=item.getJSONObject("user");
                   String username=user.getString("username");
                    String profileImg=user.getString("profile_image");
                    videoOwner=new UserModel(token,username,profileImg);

                    //setting the model
                    videosListModel=new VideosListModel(videoTitle,thumbnail,thumbnail_image,videoUrl,shareUrl,videoLength,views,likeStatus,likeCount,dislikeCount,videoOwner,createDate,id);
                    mList.add(videosListModel);
                    //saving model

                        next_object = item;
//


                }

                videosListListener.recivedVideosList(mList,next_object);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
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
            params.put("page_size", String.valueOf(page_size));
            if(last_object!=null) {
                params.put("last_element", last_object.toString());
            }

            return params;
        }






    };


}
