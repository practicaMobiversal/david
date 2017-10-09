package com.example.david.thumbsplit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.start;
import static android.R.id.edit;
import static android.os.Build.VERSION_CODES.M;
import static com.example.david.thumbsplit.MListener.curentUser;
import static com.example.david.thumbsplit.R.style.AppTheme;

public class MainActivity extends AppCompatActivity {

    private Button signIn;
    private EditText editEmail,editPass;
    private TextView forgotPass,singUp;
    private String login_url="http://52.14.245.160:4096/login";
    private AlertDialog.Builder builder;
    private String email,password;
    private static String jwtCode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 7000);
        setTheme(AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editEmail=(EditText)findViewById(R.id.edt_Email);
        editPass=(EditText)findViewById(R.id.edt_Pass);
        signIn=(Button)findViewById(R.id.btn_sing_in);
        forgotPass=(TextView)findViewById(R.id.text_forgot_pass);
        singUp=(TextView)findViewById(R.id.text_sing_up);
        builder=new AlertDialog.Builder(MainActivity.this);

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
