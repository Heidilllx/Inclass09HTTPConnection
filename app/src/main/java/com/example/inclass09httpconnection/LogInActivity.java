package com.example.inclass09httpconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity {

    public static final String LoginToken = "TOKEN";

    EditText et_email_login;
    EditText et_pswd_login;

    Button btn_login_login;
    Button btn_signup_login;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_email_login = (EditText) findViewById(R.id.et_login_email);
        et_pswd_login = (EditText) findViewById(R.id.et_login_psswd);
        btn_login_login = (Button) findViewById(R.id.button_login_login);
        btn_signup_login = (Button) findViewById(R.id.button_signup_login);
        sharedpreferences = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("email", et_email_login.getText().toString())
                        .add("password", et_pswd_login.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/login")
                        .post(formBody).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Log.d("hello", data);
                        JSONObject resultJson = (JSONObject) JSONObject.parse(data);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(Constants.AUTHORIZATION, true);
                        editor.putString("token", SignUpActivity.TOKEN_PREFIX + resultJson.get("token").toString());
                        editor.putString("user", resultJson.get("user_fname") + " " + resultJson.get("user_lname"));
                        editor.commit();

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LogInActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });

            }
        });

        btn_signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
