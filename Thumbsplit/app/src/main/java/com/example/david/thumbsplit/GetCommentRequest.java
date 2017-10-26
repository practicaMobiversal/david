package com.example.david.thumbsplit;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.CommentsListListener;
import com.example.david.thumbsplit.model.CommentsModelList;
import com.example.david.thumbsplit.model.LastElement;
import com.example.david.thumbsplit.model.UserModel;
import com.example.david.thumbsplit.model.LastElement;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emanuela on 10/16/2017.
 */

public class GetCommentRequest {
    List<CommentsModelList> commentsModelLists=new ArrayList<>();
    CommentsModelList commentsModel;
    int page_size,videoId,commentId;
    long createdAt;
    String token,comm_url="http://52.14.245.160:4096/v1/get-video-comments";;
    JSONObject last_object,next_object;
    CommentsListListener commentsListListener;
    public LastElement last_obj;
String lastParam;

    public GetCommentRequest(int video_id,String token, JSONObject last_element,int page_size,CommentsListListener commentsListListener){
        this.page_size=page_size;
        this.videoId=video_id;
        this.last_object=last_element;
        this.token=token;
        this.commentsListListener=commentsListListener;
        try {
            lastParam=last_object.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private HashMap<String, String> headers = new HashMap<>();

    public StringRequest stringRequest=new StringRequest(Request.Method.POST, comm_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object=new JSONObject(response);

                JSONObject data=object.getJSONObject("data");

                JSONArray commentsArray=data.getJSONArray("comments");
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
                commentsListListener.recivedCommentsList(commentsModelLists,next_object);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    }){


        // corp functie
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            if (headers == null) headers = new HashMap<>();
            if (!TextUtils.isEmpty(token)) {
                headers.put("Authorization", String.format("Bearer %s", token));
            }
            headers.put("content-type", "application/json");
            return headers;
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            String pram="{\"last_element\": {\n                    \"created_at\": " +lastParam.toString() + "\n                }, \n                \"video_id\":" + String.valueOf(videoId) + ",\n                \"page_size\":3}";
            return pram.getBytes();

        }

//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String, String> params=new HashMap<>();
//            params.put("video_id", String.valueOf(videoId));
//            params.put("page_size", String.valueOf(page_size));
//            if(last_object!=null) {
//                params.put("last_element", "{created_at:1507808837734}");
//            }
//            return params;
//        }




    };


}

