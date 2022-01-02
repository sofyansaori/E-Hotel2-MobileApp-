package com.example.mobilehotelgroup2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilehotelgroup2.models.BookingHistoryModelResponse;
import com.example.mobilehotelgroup2.models.KamarModelResponse;
import com.example.mobilehotelgroup2.models.RegisterModelResponse;
import com.example.mobilehotelgroup2.pref.PrefHelper;
import com.example.mobilehotelgroup2.service.ApiClient;
import com.example.mobilehotelgroup2.service.ApiEndpoint;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBookingActivity extends AppCompatActivity {

    private BookingHistoryModelResponse item;
    private ApiEndpoint apiEndpoint;
    private List<KamarModelResponse> listKamar;
    private List<String> listKodeKamar;
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
    private FloatingActionButton fabUpdate;
    private FloatingActionButton fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamar_edit);

        initView();
    }

    private void initView() {
        item = getIntent().getParcelableExtra("bookingData");
        apiEndpoint = new ApiClient().getRetrofit().create(ApiEndpoint.class);
        prefHelper = new PrefHelper(this);
        listKodeKamar = new ArrayList<>();
        calendar = Calendar.getInstance();

        etKodeKamar = findViewById(R.id.et_kode_kamar);
        spinKamar = findViewById(R.id.spin_kamar);
        etHargaPerMalam = findViewById(R.id.et_harga_permalam);
        etTglCheckIn = findViewById(R.id.et_tgl_checkin);
        etTglCheckout = findViewById(R.id.et_tgl_checkout);
        ibtCheckin = findViewById(R.id.ibt_tgl_checkin);
        ibtCheckout = findViewById(R.id.ibt_tgl_checkout);
        fabUpdate = findViewById(R.id.fab_update_booking);
        fabDelete = findViewById(R.id.fab_delete_booking);

        etKodeKamar.setText(item.getKodeHotel());
        etHargaPerMalam.setText(item.getHargaPermalam());
        etTglCheckIn.setText(item.getCheckIn());
        etTglCheckout.setText(item.getCheckOut());

        ibtCheckin.setOnClickListener(View -> {
            showTglCheckInPicker();
        });
        ibtCheckout.setOnClickListener(View -> {
            showTglCheckoutPicker();
        });
        fabUpdate.setOnClickListener(View -> {
            saveTransaction(2);
        });
        fabDelete.setOnClickListener(View -> {
            saveTransaction(3);
        });

        getDataKamarFromServer();
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
                    Toast.makeText(EditBookingActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KamarModelResponse>> call, Throwable t) {
                Toast.makeText(EditBookingActivity.this, "Terjadi Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataToView() {
        String[] arrKamar = new String[listKamar.size()];

        for (int i = 0; i < listKamar.size(); i ++) {
            arrKamar[i] = listKamar.get(i).getTypeKamar();
            listKodeKamar.add(listKamar.get(i).getKodeHotel());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                arrKamar
        ){
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinKamar.setSelection(listKodeKamar.indexOf(item.getKodeHotel()));
    }

    private void saveTransaction(int type) {
        Call<RegisterModelResponse> callTransaction = apiEndpoint.transaction(
                type,
                item.getIdTransaksi(),
                selectedIdKamar,
                prefHelper.getUser().getIdUser(),
                etTglCheckIn.getText().toString(),
                etTglCheckout.getText().toString()
        );

        callTransaction.enqueue(new Callback<RegisterModelResponse>() {
            @Override
            public void onResponse(Call<RegisterModelResponse> call, Response<RegisterModelResponse> response) {
                RegisterModelResponse registerModelResponse = response.body();

                if (registerModelResponse != null){
                    Toast.makeText(EditBookingActivity.this, registerModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditBookingActivity.this, "Terjadi Kesalahan Koneksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModelResponse> call, Throwable t) {
                Toast.makeText(EditBookingActivity.this, "Err: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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