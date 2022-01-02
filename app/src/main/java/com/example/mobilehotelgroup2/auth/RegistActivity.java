package com.example.mobilehotelgroup2.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilehotelgroup2.R;
import com.example.mobilehotelgroup2.models.RegisterModelResponse;
import com.example.mobilehotelgroup2.service.ApiClient;
import com.example.mobilehotelgroup2.service.ApiEndpoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistActivity extends AppCompatActivity {

    ApiEndpoint apiEndpoint;

    private EditText etUsername;
    private EditText etPassword;
    private EditText etRepassword;
    private Button btRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
    }

    private void initView() {
        apiEndpoint = new ApiClient().getRetrofit().create(ApiEndpoint.class);

        etUsername = findViewById(R.id.et_username_register);
        etPassword = findViewById(R.id.et_pass_register);
        etRepassword = findViewById(R.id.et_repass_register);
        btRegister = findViewById(R.id.bt_register);
        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(View -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btRegister.setOnClickListener(view -> {
            String userName = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String rePassword = etRepassword.getText().toString();

            if (password.equals(rePassword)) {
                regiter(userName, password);
            } else {
                etRepassword.setError("Sandi tidak sama");
            }

        });
    }

    private void regiter(String userName, String password) {
        Call<RegisterModelResponse> callRegister = apiEndpoint.register(userName, password);
        callRegister.enqueue(new Callback<RegisterModelResponse>() {
            @Override
            public void onResponse(Call<RegisterModelResponse> call, Response<RegisterModelResponse> response) {
                RegisterModelResponse registerModelResponse = response.body();

                if (registerModelResponse != null) {
                    Toast.makeText(RegistActivity.this, registerModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegistActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModelResponse> call, Throwable t) {
                Toast.makeText(RegistActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}