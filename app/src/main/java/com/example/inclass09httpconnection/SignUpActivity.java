package com.example.inclass09httpconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.inclass09httpconnection.LogInActivity.LoginToken;

public class SignUpActivity extends AppCompatActivity {


    EditText et_first_name;
    EditText et_last_name;
    EditText et_email_su;
    EditText et_pswd_su;
    EditText et_confirm_pswd_su;
    Button btn_signup_su;
    SharedPreferences sharedpreferences;

    public static final String TOKEN_PREFIX = "BEARER ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_first_name = (EditText) findViewById(R.id.et_first_name_signup);
        et_last_name = (EditText) findViewById(R.id.et_last_name_signup);
        et_pswd_su = (EditText) findViewById(R.id.et_pswd_signup);
        et_email_su = (EditText) findViewById(R.id.et_email_signup);
        et_confirm_pswd_su = (EditText) findViewById(R.id.et_confirm_pswd_signup);


        btn_signup_su = (Button) findViewById(R.id.btn_signup_signup);
        sharedpreferences = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);

        btn_signup_su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder()
                        .add("fname", et_first_name.getText().toString())
                        .add("lname", et_last_name.getText().toString())
                        .add("email", et_email_su.getText().toString())
                        .add("password", et_pswd_su.getText().toString());


                Request request = new Request.Builder()
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup")
                        .post(formBody.build()).build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (201 == response.code()) {
                            String data = response.body().string();
                            JSONObject resultJson = (JSONObject) JSONObject.parse(data);
                            persist(resultJson);
                            Toast.makeText(SignUpActivity.this, "create user successfully!", Toast.LENGTH_SHORT);
                        }
//                        Intent inbox = new Intent(SignUpActivity.this, InBoxActivity.class);

                        finish();
                    }
                });
            }
        });

    }

    /**
     * store the object into shared preferences
     * *
     */
    private void persist(JSONObject resultJson) {
        SharedPreferences sharedpreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constants.AUTHORIZATION, true);
        editor.putString("token", TOKEN_PREFIX + resultJson.get("token").toString());
        editor.putString("user", resultJson.get("user_fname") + " " + resultJson.get("user_lname"));
        editor.commit();
    }
}
