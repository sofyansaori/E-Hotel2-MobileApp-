package com.example.mobilehotelgroup2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mobilehotelgroup2.auth.LoginActivity;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}