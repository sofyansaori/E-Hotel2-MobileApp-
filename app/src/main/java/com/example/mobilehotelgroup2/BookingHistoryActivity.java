package com.example.mobilehotelgroup2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobilehotelgroup2.adapter.RecyclerBookingHistoryAdapter;
import com.example.mobilehotelgroup2.models.BookingHistoryModelResponse;
import com.example.mobilehotelgroup2.pref.PrefHelper;
import com.example.mobilehotelgroup2.service.ApiClient;
import com.example.mobilehotelgroup2.service.ApiEndpoint;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryActivity extends AppCompatActivity {

    private ApiEndpoint apiEndpoint;
    private List<BookingHistoryModelResponse> listBookingHistory;
    private PrefHelper prefHelper;
    private RecyclerBookingHistoryAdapter recAdapter;

    private RecyclerView recHistory;
    private FloatingActionButton fabAddBoking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getDataFromServer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getDataFromServer();
    }

    private void getDataFromServer() {
        apiEndpoint.bookingHistory(prefHelper.getUser().getIdUser()).enqueue(new Callback<List<BookingHistoryModelResponse>>() {
            @Override
            public void onResponse(Call<List<BookingHistoryModelResponse>> call, Response<List<BookingHistoryModelResponse>> response) {
                listBookingHistory = response.body();

                if (listBookingHistory.size() != 0) {
                    setRecyclerData();
                }
            }

            @Override
            public void onFailure(Call<List<BookingHistoryModelResponse>> call, Throwable t) {

            }
        });
    }

    private void setRecyclerData() {
        recAdapter = new RecyclerBookingHistoryAdapter(this, listBookingHistory);
        recHistory.setLayoutManager(new LinearLayoutManager(this));
        recHistory.setAdapter(recAdapter);
    }

    private void initView() {
        apiEndpoint = new ApiClient().getRetrofit().create(ApiEndpoint.class);
        prefHelper = new PrefHelper(this);

        recHistory = findViewById(R.id.rec_booking_history);
        fabAddBoking = findViewById(R.id.fab_add_booking);

        fabAddBoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingHistoryActivity.this, BookingActivity.class));
            }
        });
    }
}