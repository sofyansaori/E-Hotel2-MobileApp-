package com.example.mobilehotelgroup2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilehotelgroup2.EditBookingActivity;
import com.example.mobilehotelgroup2.R;
import com.example.mobilehotelgroup2.models.BookingHistoryModelResponse;

import java.util.List;

public class RecyclerBookingHistoryAdapter extends RecyclerView.Adapter<RecyclerBookingHistoryAdapter.ViewHolder> {

    Context context;
    List<BookingHistoryModelResponse> listBookingHistory;

    public RecyclerBookingHistoryAdapter(Context context, List<BookingHistoryModelResponse> listBookingHistory) {
        this.context = context;
        this.listBookingHistory = listBookingHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.list_kamar, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(listBookingHistory.get(position));
    }

    @Override
    public int getItemCount() {
        return listBookingHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private TextView tvIdTypeKamar;
        private TextView tvKodeKamar;
        private TextView tvCheckIn;
        private TextView tvCheckoOut;
        private TextView tvHargaPermalam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.rel_item_history_booking);
            tvIdTypeKamar = itemView.findViewById(R.id.tv_type_kamar_list_kamar);
            tvKodeKamar = itemView.findViewById(R.id.tv_kode_kamar);
            tvCheckIn = itemView.findViewById(R.id.tv_checkin);
            tvCheckoOut = itemView.findViewById(R.id.tv_checkout);
            tvHargaPermalam = itemView.findViewById(R.id.tv_harga_history_booking);
        }

        void setData(BookingHistoryModelResponse item) {
            tvIdTypeKamar.setText(item.getTypeKamar());
            tvKodeKamar.setText(item.getKodeHotel());
            tvCheckIn.setText(item.getCheckIn());
            tvCheckoOut.setText(item.getCheckOut());
            tvHargaPermalam.setText("Rp. " + item.getHargaPermalam());

            container.setOnClickListener(View -> {
                Intent i = new Intent(context, EditBookingActivity.class);
                i.putExtra("bookingData", item);
                context.startActivity(i);
            });
        }
    }
}
