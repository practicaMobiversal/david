package com.example.david.thumbsplit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 9/26/2017.
 */

public class MListener {
    public int code;
    public String token;
    private My1Listener codeListener;
    private String email,password;
    public static UserModel curentUser;
    private AlertDialog.Builder builder;
    private String login_url="http://52.14.245.160:4096/login";

    private HashMap<String, String> headers = new HashMap<>();

    public MListener(String email, String password, AlertDialog.Builder builder,My1Listener codeListener) {
        this.email = email;
        this.password = password;
        this.codeListener=codeListener;
        this.builder=builder;

    }



    StringRequest stringRequest=new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {


        @Override
        public void onResponse(String response) {

            try {

                JSONObject jsonObject=new JSONObject(response);
                JSONObject json1Object=jsonObject.getJSONObject("status");

                code=json1Object.getInt("code");
                token=jsonObject.getString("jwt");
                if(code>=500){
                    builder.setTitle("Server Response...");
                    builder.setMessage("User not found");
                }
                codeListener.recivedJWT(token);
                codeListener.recivedCodeFromServer(code);


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
        protected Map<String, String> getParams() throws AuthFailureError {
            Map <String,String> params=new HashMap<String, String>();
            params.put("email",email);
            params.put("password",password);
            return params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            return headers;
        }
    };



}
