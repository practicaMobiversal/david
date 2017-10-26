package com.example.david.thumbsplit;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.CurentUser;
import com.example.david.thumbsplit.model.SocialListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emanuela on 10/24/2017.
 */

class SocialRegisterRequest {

    String social_register_url = "http://52.14.245.160:4096/v1/social-register";
    CurentUser curentUser;
    SocialListener listener;
    String token;

    SocialRegisterRequest(CurentUser curentUser, SocialListener socialListener) {
        this.curentUser = curentUser;
        this.listener = socialListener;
    }

    private HashMap<String, String> headers = new HashMap<>();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, social_register_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int code = jsonObject.getJSONObject("status").getInt("code");
                if (code == 200) {
Toast.makeText((Context) listener,"successs",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("email", curentUser.getEmail());
            params.put("type", String.valueOf(1));
            params.put("token", String.valueOf(curentUser.getToken()));
            return params;

        }
    };
}