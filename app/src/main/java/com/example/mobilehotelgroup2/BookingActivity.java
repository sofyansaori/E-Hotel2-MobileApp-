package com.example.mobilehotelgroup2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilehotelgroup2.models.KamarModelResponse;
import com.example.mobilehotelgroup2.models.RegisterModelResponse;
import com.example.mobilehotelgroup2.pref.PrefHelper;
import com.example.mobilehotelgroup2.service.ApiClient;
import com.example.mobilehotelgroup2.service.ApiEndpoint;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    private ApiEndpoint apiEndpoint;
    private List<KamarModelResponse> listKamar;
    private PrefHelper prefHelper;
    private Calendar calendar;
    private String selectedIdKamar;

    private EditText etKodeKamar;
    private Spinner spinKamar;
    private EditText etHargaPerMalam;
    private EditText etTglCheckIn;
    private EditText etTglCheckout;
    private ImageButton ibtCheckin;
    private ImageButton ibtCheckout;
    private Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kamar);

        initView();
    }

    private void initView() {
        apiEndpoint = new ApiClient().getRetrofit().create(ApiEndpoint.class);
        prefHelper = new PrefHelper(this);
        calendar = Calendar.getInstance();

        etKodeKamar = findViewById(R.id.et_kode_kamar);
        spinKamar = findViewById(R.id.spin_kamar);

        etHargaPerMalam = findViewById(R.id.et_harga_permalam);
        etTglCheckIn = findViewById(R.id.et_tgl_checkin);
        etTglCheckout = findViewById(R.id.et_tgl_checkout);
        ibtCheckin = findViewById(R.id.ibt_tgl_checkin);
        ibtCheckout = findViewById(R.id.ibt_tgl_checkout);
        btnBooking = findViewById(R.id.bt_booking);

        etTglCheckIn.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE));
        etTglCheckout.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE));

        getDataKamarFromServer();

        ibtCheckin.setOnClickListener(View -> {
            showTglCheckInPicker();
        });
        ibtCheckout.setOnClickListener(View -> {
            showTglCheckoutPicker();
        });
        btnBooking.setOnClickListener(view -> {
            saveTransaction();
        });
    }

    private void saveTransaction() {
        Call<RegisterModelResponse> callTransaction = apiEndpoint.transaction(
                1,
                "",
                selectedIdKamar,

                prefHelper.getUser().getIdUser(),
                etTglCheckIn.getText().toString(),
                etTglCheckout.getText().toString()
        );

        callTransaction.enqueue(new Callback<RegisterModelResponse>() {
            @Override
            public void onResponse(Call<RegisterModelResponse> call, Response<RegisterModelResponse> response) {
                RegisterModelResponse registerModelResponse = response.body();

                if (registerModelResponse != null) {
                    Toast.makeText(BookingActivity.this, registerModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookingActivity.this, "Terjadi Kesalahan Koneksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModelResponse> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Err: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataKamarFromServer() {
        Call<List<KamarModelResponse>> callListKamar = apiEndpoint.listKamar();
        callListKamar.enqueue(new Callback<List<KamarModelResponse>>() {
            @Override
            public void onResponse(Call<List<KamarModelResponse>> call, Response<List<KamarModelResponse>> response) {
                listKamar = response.body();

                if (listKamar.size() != 0) {
                    setDataToView();
                }else {
                    Toast.makeText(BookingActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KamarModelResponse>> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Terjadi Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataToView() {
        String[] arrKamar = new String[listKamar.size()];
        for (int i = 0; i < listKamar.size(); i++) {
            arrKamar[i] = listKamar.get(i).getTypeKamar();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrKamar) {

            @Override
            public boolean isEnabled(int position) {
                return position != 1;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                if (position == 1) {
                    textView.setTextColor(getResources().getColor(R.color.teal_200));
                    textView.setEnabled(false);
                }
                return textView;
            }
        };
        spinKamar.setAdapter(adapter);
        spinKamar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etKodeKamar.setText(listKamar.get(i).getKodeHotel());
                etHargaPerMalam.setText("Rp. " + listKamar.get(i).getHargaPermalam());
                selectedIdKamar = listKamar.get(i).getIdKamar();
                if (listKamar.get(i).getStokKamar().equals("0")) {
                  btnBooking.setEnabled(false);

                    Toast.makeText(BookingActivity.this, "Kamar Habis Terbooking : " , Toast.LENGTH_SHORT).show();
                }else{
                    btnBooking.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showTglCheckInPicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                etTglCheckIn.setText(year + "-" + (month + 1) + "-" + date);
                showTimeCheckInPicker();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void showTimeCheckInPicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etTglCheckIn.setText(etTglCheckIn.getText().toString() + " " + hour + ":" + minute);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void showTglCheckoutPicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                etTglCheckout.setText(year + "-" + (month + 1) + "-" + date);
                showTimeCheckOutPicker();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void showTimeCheckOutPicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etTglCheckout.setText(etTglCheckout.getText().toString() + " " + hour + ":" + minute);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}