package com.example.david.thumbsplit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.david.thumbsplit.model.VideosListModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.Set;

import static com.example.david.thumbsplit.R.style.SplashTheme;

public class ShareVideoActivity extends AppCompatActivity {

    VideosListModel sharedVideo;
    private CallbackManager facebookCallbackManager;
    private LoginManager loginManager;
    private String downloadedFilePath;
    String share_url,videoTitle;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SplashTheme);
        setContentView(R.layout.activity_share_video);
        progress=(ProgressBar)findViewById(R.id.pbHeaderProgress);
        Intent inte=getIntent();
        share_url=inte.getStringExtra("share_url");
        videoTitle=inte.getStringExtra("title");
        this.prepareUploadToFacebook();

        progress = new ProgressBar(ShareVideoActivity.this, null, android.R.attr.progressBarStyleHorizontal);
        progress.setVisibility(View.VISIBLE);
    }

    private void prepareUploadToFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null)
            requestPublishPermissionWhenLogin();
        else
        {
            Set<String> permissions = AccessToken.getCurrentAccessToken().getPermissions();

            if (permissions.contains("publish_actions")) {
                //uploadToFacebook directly to fb
                uploadToFacebook(accessToken);
            } else {
               // request publish_actions permission
                requestPublishPermissionWhenLogin();
            }
        }
    }

    private void requestPublishPermissionWhenLogin() {
        facebookCallbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.logOut();
        loginManager.logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        loginManager.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              //  Facebook login success
                AccessToken accessToken = loginResult.getAccessToken();
                if (accessToken.getPermissions().contains("publish_actions")){
                    uploadToFacebook(accessToken);
                } else {
               //     AppMediaStorage.deleteFile(downloadedFilePath);
                   finish();
                }
            }

            @Override
            public void onCancel() {
            //    Facebook login cancel
                finish();
            }

            @Override
            public void onError(FacebookException error) {
             //   Facebook login error
                finish();
            }
        });
    }
    private void uploadToFacebook(AccessToken accessToken) {
        Bundle parameters = new Bundle();
        parameters.putString("title", "");
        parameters.putString("description",videoTitle);
        parameters.putString("file_url", share_url);

        GraphRequest request = new GraphRequest(accessToken, "me/videos", parameters, HttpMethod.POST, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
               // GraphResponse
                Intent fullscreen=new Intent(ShareVideoActivity.this,VideoDetailActivity.class);
                setResult(RESULT_OK,fullscreen);
                finish();
            }
        });

        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
