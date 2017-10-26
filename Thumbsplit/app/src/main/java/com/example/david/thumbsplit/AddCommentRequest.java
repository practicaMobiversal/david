package com.example.david.thumbsplit;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.AddNewCommentListener;
import com.example.david.thumbsplit.model.CommentsListListener;
import com.example.david.thumbsplit.model.CommentsModelList;
import com.example.david.thumbsplit.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emanuela on 10/17/2017.
 */

public class AddCommentRequest {
    int videoId,commentId;
    CommentsModelList commentsModel;
    String token,text,url="http://52.14.245.160:4096/v1/add-comment";
    AddNewCommentListener addCommentListener;



    public AddCommentRequest(int videoId,String token,String text,AddNewCommentListener addCommentListener){
        this.videoId=videoId;
        this.text=text;
        this.token=token;
        this.addCommentListener=addCommentListener;
    }
    private HashMap<String, String> headers = new HashMap<>();

    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject=new JSONObject(response).getJSONObject("status");
                int code=jsonObject.getInt("code");
                JSONObject jason=new JSONObject(response).getJSONObject("data").getJSONObject("comment");
                int commentId=jason.getInt("id");
                String Text=jason.getString("text");
                int createdAt=jason.getInt("created_at");
                int likes=jason.getInt("like_count");
                int like_stats=jason.getInt("like_status");
                int dislikes=jason.getInt("dislike_count");
                JSONObject commUser=jason.getJSONObject("user");
                String comUserName=commUser.getString("username");
                String comProfileImage= commUser.getString("profile_image");
                UserModel commentOwner=new UserModel("0",comUserName,comProfileImage);
                commentsModel=new CommentsModelList(text,like_stats,dislikes,likes,createdAt,commentOwner,commentId);


                addCommentListener.addNewComment(commentsModel);
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
            Map<String, String> params=new HashMap<>();
            params.put("video_id", String.valueOf(videoId));
            params.put("text", text);
            return params;
        }


    };
}
