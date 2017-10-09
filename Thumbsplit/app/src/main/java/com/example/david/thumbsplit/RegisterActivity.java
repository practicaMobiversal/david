package com.example.david.thumbsplit;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.password;
import static android.R.id.message;

public class RegisterActivity extends AppCompatActivity {


   private Button singUp;
    private RadioButton radioTerms;
    private  EditText editUsername,editName,editEmail,editPass,editConfirmPass;
   private TextView textTerms;
    private AlertDialog.Builder builder;
    String name,username,email,pass,confirmPass;
    TextView mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       mActionBar=(TextView) findViewById(R.id.loginText);
        mActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        editEmail=(EditText)findViewById(R.id.edt_reg_email);
        editName=(EditText)findViewById(R.id.edt_reg_name);
        editUsername=(EditText)findViewById(R.id.edt_reg_user);
        editPass=(EditText)findViewById(R.id.edt_reg_pass);
        editConfirmPass=(EditText)findViewById(R.id.edt_reg_confirm_pass);
        textTerms=(TextView)findViewById(R.id.text_terms);
        radioTerms=(RadioButton)findViewById(R.id.radio_terms);
        singUp=(Button)findViewById(R.id.btn_sing_up);
        builder=new AlertDialog.Builder(RegisterActivity.this);


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=editName.getText().toString();
                email=editEmail.getText().toString();
                username=editUsername.getText().toString();
                pass=editPass.getText().toString();
                confirmPass=editConfirmPass.getText().toString();

                if(name.equals("")||email.equals("")||username.equals("")||pass.equals("")||confirmPass.equals("")){
                builder.setTitle("something went wrong...");
                    builder.setMessage("fill all the fields...");
                displayAlert(300);
                }else if(!(radioTerms.isChecked())){
                    builder.setTitle("something went wrong");
                    builder.setMessage("Accept Terms&Conditions");
                    displayAlert(300);
                }
                else{
                    if(!(pass.equals(confirmPass))){
                        builder.setTitle("something went wrong...");
                        builder.setMessage("Passwords not matching...");
                        displayAlert(300);

                    }
                    else{
                     RListener listener=new RListener(name, username, pass, email, builder, new MyListener() {
                         @Override
                         public void recivedCodeFromServer(int code) {
                             displayAlert(code);
                         }
                     });
                        StringRequest arg = listener.stringRequest;
                        MySingleton.getInstance(RegisterActivity.this).addToRequestque(arg);

                    }

                }

            }
        });





    }

    public void displayAlert(final int code){
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            if(code==300){
                editPass.setText("");
                editConfirmPass.setText("");
            }
            else if (code==200){
                finish();
            }
            else if (code>=500){
                editPass.setText("");
                editConfirmPass.setText("");
                editName.setText("");
                editUsername.setText("");
                editEmail.setText("");

            }
        }
    });
AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }
}
