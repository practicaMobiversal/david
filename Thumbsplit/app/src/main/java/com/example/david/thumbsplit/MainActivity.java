package com.example.david.thumbsplit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.CurentUser;
import com.example.david.thumbsplit.model.My1Listener;
import com.example.david.thumbsplit.model.RSocialToken;
import com.example.david.thumbsplit.model.SocialListener;
import com.example.david.thumbsplit.model.UserModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.david.thumbsplit.R.style.AppTheme;
import static com.example.david.thumbsplit.R.style.SplashTheme;

public class MainActivity extends AppCompatActivity implements SocialListener{

    private Button signIn;
    private EditText editEmail,editPass;
    private TextView forgotPass,singUp;
    private String login_url="http://52.14.245.160:4096/login";
    private AlertDialog.Builder builder;
    private String email,password;
    private static String jwtCode;
    private ImageView loginFacebook;
    LoginButton loginButton;
    CallbackManager callbackManager;
    CurentUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SplashTheme);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 10000);
        setTheme(AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editEmail=(EditText)findViewById(R.id.edt_Email);
        editPass=(EditText)findViewById(R.id.edt_Pass);
        signIn=(Button)findViewById(R.id.btn_sing_in);
        forgotPass=(TextView)findViewById(R.id.text_forgot_pass);
        singUp=(TextView)findViewById(R.id.text_sing_up);
        loginFacebook=(ImageView)findViewById(R.id.login_facebook);
        loginButton=(LoginButton)findViewById(R.id.login_button);
        builder=new AlertDialog.Builder(MainActivity.this);
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot=new Intent(MainActivity.this,ForgotActivity.class);
                startActivity(forgot);
            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singup=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(singup);
            }
        });
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        loginWithFacebook();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=editEmail.getText().toString();
                password=editPass.getText().toString();

                if(email.equals("")||password.equals("")){
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Fill all fields");
                    displayAlert(300);
                }else{
                    MListener listener=new MListener(email, password, builder, new My1Listener() {

                        @Override
                        public void recivedJWT(String token) {

                            jwtCode=token;
                        }
                        @Override
                        public void recivedCodeFromServer(int code) {
                            if(code!=200)
                                displayAlert(code);
                            else if(code==200)
                            {
                                Intent login=new Intent(MainActivity.this,HomeActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("jwt",jwtCode);
                                login.putExtras(bundle);
                                startActivity(login);

                            }
                        }
                    });
                    StringRequest arg=listener.stringRequest;
                    MySingleton.getInstance(MainActivity.this).addToRequestque(arg);
                }

            }
        });

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithFacebook(){

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                getEmailFromFacebook(accessToken);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }
    private void getEmailFromFacebook(final AccessToken facebookAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(facebookAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // "Result from facebook getMeRequest response:

                String email = "";
                try {
                    email = object.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tryFacebookLogin(facebookAccessToken, email);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void tryFacebookLogin(AccessToken facebookAccessToken, String email) {
        RSocialToken token = new RSocialToken(facebookAccessToken.getToken());
        String userId = facebookAccessToken.getUserId();
        makeSocialLoginRequest(1, token, userId, email);
    }


    private void makeSocialLoginRequest(int type, RSocialToken token, String userId, String email) {
        currentUser = new CurentUser();
        currentUser.setToken(token);
        currentUser.setUserId(userId);
        currentUser.setEmail(email);
        SocialLoginRequest request = new SocialLoginRequest(currentUser, this);

        StringRequest arg=request.stringRequest;
        MySingleton.getInstance(MainActivity.this).addToRequestque(arg);

    }
    @Override
    public void onLoggedUser(String jwtToken){
        Intent login=new Intent(MainActivity.this,HomeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("jwt",jwtToken);
        login.putExtras(bundle);
        startActivity(login);


    };
    public void onRegisteredUser(String jwtToken){};
    @Override
    public void trySocialRegister(){
        if (!TextUtils.isEmpty(currentUser.getEmail()))
        makeSocialRegisterRequest();
    //else
      //  openRequestMailPopUp();
    }

    private void makeSocialRegisterRequest() {
        SocialRegisterRequest request = new SocialRegisterRequest(currentUser, this);
        StringRequest arg=request.stringRequest;
        MySingleton.getInstance(MainActivity.this).addToRequestque(arg);
    }


private void displayAlert(final int code) {
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if(code==300){
                editPass.setText("");

            }
            else if(code>=500){
                editPass.setText("");
                editEmail.setText("");
            }
        }
    });
    AlertDialog alertDialog= builder.create();
    alertDialog.show();

    }



}
