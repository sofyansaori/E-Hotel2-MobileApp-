package com.example.mobilehotelgroup2.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BookingHistoryModelResponse implements Parcelable {

    @SerializedName("id_transaksi")
    private String idTransaksi;
    @SerializedName("kodehotel")
    private String kodeHotel;
    @SerializedName("typekamar")
    private String typeKamar;
    @SerializedName("hargapermalam")
    private String hargaPermalam;
    @SerializedName("checkin")
    private String checkIn;
    @SerializedName("checkout")
    private String checkOut;

    public BookingHistoryModelResponse() {
    }

    public BookingHistoryModelResponse(String idTransaksi, String kodeHotel, String typeKamar, String hargaPermalam, String checkIn, String checkOut) {
        this.idTransaksi = idTransaksi;
        this.kodeHotel = kodeHotel;
        this.typeKamar = typeKamar;
        this.hargaPermalam = hargaPermalam;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    protected BookingHistoryModelResponse(Parcel in) {
        idTransaksi = in.readString();
        kodeHotel = in.readString();
        typeKamar = in.readString();
        hargaPermalam = in.readString();
        checkIn = in.readString();
        checkOut = in.readString();
    }

    public static final Creator<BookingHistoryModelResponse> CREATOR = new Creator<BookingHistoryModelResponse>() {
        @Override
        public BookingHistoryModelResponse createFromParcel(Parcel in) {
            return new BookingHistoryModelResponse(in);
        }

        @Override
        public BookingHistoryModelResponse[] newArray(int size) {
            return new BookingHistoryModelResponse[size];
        }
    };

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getKodeHotel() {
        return kodeHotel;
    }

    public void setKodeHotel(String kodeHotel) {
        this.kodeHotel = kodeHotel;
    }

    public String getTypeKamar() {
        return typeKamar;
    }

    public void setTypeKamar(String typeKamar) {
        this.typeKamar = typeKamar;
    }

    public String getHargaPermalam() {
        return hargaPermalam;
    }

    public void setHargaPermalam(String hargaPermalam) {
        this.hargaPermalam = hargaPermalam;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idTransaksi);
        parcel.writeString(kodeHotel);
        parcel.writeString(typeKamar);
        parcel.writeString(hargaPermalam);
        parcel.writeString(checkIn);
        parcel.writeString(checkOut);
    }
}
