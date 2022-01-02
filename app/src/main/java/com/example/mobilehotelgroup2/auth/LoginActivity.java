package com.example.mobilehotelgroup2.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilehotelgroup2.HomeActivity;
import com.example.mobilehotelgroup2.R;
import com.example.mobilehotelgroup2.models.LoginModelResponse;
import com.example.mobilehotelgroup2.pref.PrefHelper;
import com.example.mobilehotelgroup2.service.ApiClient;
import com.example.mobilehotelgroup2.service.ApiEndpoint;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private ApiEndpoint apiEndpoint;
    private PrefHelper prefHelper; //kind of session in web programming but not similar

    EditText username;
    EditText password;
    ProgressDialog pDialog;
    Button buttonLogin;
    TextView tvRegist;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        initView();
        checkUser();

        //inisialisasi tampilan

        buttonLogin.setOnClickListener(view -> {
            showDialog();

            Call<LoginModelResponse> call = apiEndpoint.login(username.getText().toString(), password.getText().toString());
            call.enqueue(new Callback<LoginModelResponse>() {
                @Override
                public void onResponse(Call<LoginModelResponse> call, retrofit2.Response<LoginModelResponse> response) {
                    LoginModelResponse loginModel = response.body();

                    if (loginModel != null) {
                        prefHelper.saveUser(loginModel);

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }

                    hideDialog();
                }

                @Override
                public void onFailure(Call<LoginModelResponse> call, Throwable t) {

                    hideDialog();
                }
            });
        });

    }

    private void checkUser() {
        LoginModelResponse user = prefHelper.getUser();
        if (user.getUsername() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void initView() {
        apiEndpoint = new ApiClient().getRetrofit().create(ApiEndpoint.class);
        prefHelper = new PrefHelper(this);

        pDialog = new ProgressDialog(context);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        tvRegist = findViewById(R.id.tv_regist);

        tvRegist.setOnClickListener(View -> {
            startActivity(new Intent(this, RegistActivity.class));
            finish();
        });
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}