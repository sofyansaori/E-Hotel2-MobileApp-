package com.example.mobilehotelgroup2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilehotelgroup2.auth.LoginActivity;
import com.example.mobilehotelgroup2.pref.PrefHelper;

public class HomeActivity extends AppCompatActivity {

    PrefHelper prefHelper;

    Button btnInput;
    Button btnList;
    Button btnLogout;
    TextView textJudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
        prefHelper = new PrefHelper(this);

        btnList = findViewById(R.id.btnList);
        btnInput = findViewById(R.id.btnInput);
        btnLogout = findViewById(R.id.btn_logout);
        textJudul = findViewById(R.id.text_judul_home_activity);

        setUserData();

        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BookingHistoryActivity.class);
            startActivity(intent);
        });
        btnInput.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(View -> {
           prefHelper.removeUser();
           startActivity(new Intent(this, LoginActivity.class));
           finish();
        });
    }

    private void setUserData() {
        textJudul.setText("Welcome, " + prefHelper.getUser().getUsername());
    }
}