package com.example.david.thumbsplit;

import android.content.Intent;

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

public class copy {

//    StringRequest stringRequest=new StringRequest(Request.Method.POST, reg_url, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//
//            try {
//
//                JSONObject jsonObject=new JSONObject(response).getJSONObject("status");
//                int code=jsonObject.getInt("code");
//                String message=jsonObject.getString("msg");
//                builder.setTitle("Servor Response...");
//                builder.setMessage(message);
//                displeyAlert(code);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }},new Response.ErrorListener(){
//
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//
//        }
//    }){
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String,String>params=new HashMap<String,String>();
//            params.put("fullname",name);
//            params.put("email",email);
//            params.put("username",username);
//            params.put("password",pass);
//            return params;
//        }
//    };


//-----------------------------main-----------


//    StringRequest stringRequest=new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//
//            try {
//                JSONObject jsonObject=new JSONObject(response).getJSONObject("status");
//                code=jsonObject.getInt("code");
//                if(code>=500){
//                    builder.setTitle("Server Response...");
//                    builder.setMessage("User not found");
//                    displayAlert(code);
//                }
//                else if(code==200){
//                    Intent login=new Intent(MainActivity.this,HomeActivity.class);
//                    startActivity(login);
//
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//
//        }
//    }){
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map <String,String> params=new HashMap<String, String>();
//            params.put("email",email);
//            params.put("password",password);
//            return params;
//        }
//    };
}
