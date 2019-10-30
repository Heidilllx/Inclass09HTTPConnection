package com.example.inclass09httpconnection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.inclass09httpconnection.LogInActivity.LoginToken;

public class AddEmailActivity extends AppCompatActivity {
    Button send;
    Button cancel;
    EditText email_text;
    Spinner spinner;
    ArrayList<String> list=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_email);

        spinner =(Spinner)findViewById(R.id.spinner);
        //adapter = new ArrayAdapter<>()；

        email_text = (EditText) findViewById(R.id.et_email_text_add_email);
        String text = email_text.getText().toString();

        cancel = (Button) findViewById(R.id.btn_cancel_addemail);

        send = (Button) findViewById(R.id.btn_send_addemail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);
                String token = pref.getString("token", "");

                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("text", email_text.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .addHeader("x-access-token", token)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/add")
                        .post(formBody).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("Add Email OnResponse", "Message Received");

                    }
                });

                finish();
            }
        });


    }

    /**
     * query receivers
     *
     * @param token
     * @return
     */
    public List<String> queryReceivers(String token) {
        List<String> resultList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        //get registered users
        Request request = new Request.Builder()
                .addHeader(Constants.AUTHORIZATION, token)
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/users").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Log.d("hello", data);
            }
        });
        return resultList;
    }
}
