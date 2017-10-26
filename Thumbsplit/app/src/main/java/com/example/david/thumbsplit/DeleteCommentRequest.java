package com.example.david.thumbsplit;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.MyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emanuela on 10/19/2017.
 */

public class DeleteCommentRequest {

    String token,deleteUrl="http://52.14.245.160:4096/v1/delete-comment";
    int commentId;
    MyListener deleteListener;

    public DeleteCommentRequest(String token,int commentId,MyListener deleteListener){
        this.token=token;
        this.commentId=commentId;
        this.deleteListener=deleteListener;
    }
    private HashMap<String, String> headers = new HashMap<>();
    StringRequest stringRequest=new StringRequest(Request.Method.POST, deleteUrl, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject=new JSONObject(response).getJSONObject("status");
                int code=jsonObject.getInt("code");
                deleteListener.recivedCodeFromServer(code);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
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
            params.put("comment_id", String.valueOf(commentId));
            return params;
        }




    };

}
