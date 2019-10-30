package com.example.inclass09httpconnection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inclass09httpconnection.adapters.DisplayEmailsAdapter;
import com.example.inclass09httpconnection.parsers.EmailParser;
import com.example.inclass09httpconnection.utils.EmailPO;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.inclass09httpconnection.LogInActivity.LoginToken;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageButton logout;

    ImageButton add_email;

    ArrayList<EmailPO> emailList = new ArrayList<>();

    TextView tv_user_name;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = (ImageButton) findViewById(R.id.ib_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        tv_user_name = (TextView) findViewById(R.id.tv_user_name_main);
        SharedPreferences pref = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);
        String token = pref.getString("token", "");

        fillUserName();

        add_email = (ImageButton) findViewById(R.id.ib_addnew);
        add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEmailActivity.class);
                startActivity(intent);
            }
        });
        //create mail list
        createRecyclerView();
    }

    /**
     * get user name from sharedpreference
     */
    private void fillUserName() {
        SharedPreferences pref = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);
        tv_user_name.setText(pref.getString("user", ""));
    }

    protected void onPostResume() {
        super.onPostResume();
        createRecyclerView();
    }

    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);
        Boolean check = pref.getBoolean(Constants.AUTHORIZATION, false);
        if (!check) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }


    /**
     * create mail list view
     */
    private void createRecyclerView() {
        SharedPreferences pref = getSharedPreferences(LoginToken, Context.MODE_PRIVATE);
        String token = pref.getString("token", "");
        OkHttpClient client = new OkHttpClient();
        Request get_emails_request = new Request.Builder()
                .addHeader(Constants.AUTHORIZATION, token)
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox").build();

        client.newCall(get_emails_request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Log.d("hello", data);
                emailList = EmailParser.parseEmails(data);
                runOnUiThread(() -> {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    DisplayEmailsAdapter.AdapterInterface adapterInterface = new DisplayEmailsAdapter.AdapterInterface() {

                        public void onDelete(int index) {

                            Request request = new Request.Builder()
                                    .addHeader(Constants.AUTHORIZATION, token)
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                    .url("http://http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/delete?msgId=" + emailList.get(index).getEmailId()).build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call1, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call1, Response response1) throws IOException {
                                    Log.d("Delete Respone", "Success");
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                            createRecyclerView();

                                        }
                                    });
                                }
                            });
                        }

                        @Override
                        public void onDisplay(int index) {
                            Log.d("display", emailList.get(index).toString());
                            Intent intent = new Intent(MainActivity.this, DisplayEmailActivity.class);
                            intent.putExtra("Key", emailList.get(index));
                            startActivity(intent);

                        }
                    };

                    RecyclerView.Adapter adapter = new DisplayEmailsAdapter(emailList,
                            adapterInterface,
                            MainActivity.this);

                    recyclerView.setAdapter(adapter);

                });


            }
        });

    }
}



