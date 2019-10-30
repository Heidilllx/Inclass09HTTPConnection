package com.example.inclass09httpconnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inclass09httpconnection.utils.EmailPO;

public class DisplayEmailActivity extends AppCompatActivity {
    TextView email_text;
    TextView email_sender;
    TextView email_subject;
    TextView email_createdAt;
    Button close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_email);

        EmailPO email_to_display = (EmailPO) getIntent().getSerializableExtra("Key");

        email_text = (TextView) findViewById(R.id.tv_email_text_display_email);
        email_text.setText(email_to_display.getMessage().toString());

        email_sender = (TextView) findViewById(R.id.tv_sender_display_email);
        email_sender.setText(email_to_display.getSenderId().toString());

        email_subject = (TextView) findViewById(R.id.tv_subject_display_email);
        email_subject.setText(email_to_display.getSubject().toString());

        email_createdAt = (TextView) findViewById(R.id.tv_created_at_display_email);
        email_createdAt.setText(email_to_display.getCreatedAt().toString());

        close = (Button) findViewById(R.id.btn_close_display_email);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
