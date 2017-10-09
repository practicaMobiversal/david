package com.example.david.thumbsplit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by David on 9/26/2017.
 */

public class RListener {
    public int code;
    private MyListener codeListener;

    public RListener(String name, String username, String pass, String email, AlertDialog.Builder builder,MyListener codeListener) {

        this.name = name;
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.builder=builder;
        this.codeListener=codeListener;
    }

    private String name,username,pass,email;
    String reg_url="http://52.14.245.160:4096/register";
    private AlertDialog.Builder builder;


    StringRequest stringRequest=new StringRequest(Request.Method.POST, reg_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {

                JSONObject jsonObject=new JSONObject(response).getJSONObject("status");
                code=jsonObject.getInt("code");
                String message=jsonObject.getString("msg");
                if (code==200){
                  builder.setTitle("Servor Response...");
                  builder.setMessage("Thanks you for Registering");
                }else {
                    builder.setTitle("Servor Response...");
                    builder.setMessage(message);
                }
                codeListener.recivedCodeFromServer(code);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }},new Response.ErrorListener(){


        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String>params=new HashMap<String,String>();
            params.put("fullname",name);
            params.put("email",email);
            params.put("username",username);
            params.put("password",pass);
            return params;
        }
    };


}
