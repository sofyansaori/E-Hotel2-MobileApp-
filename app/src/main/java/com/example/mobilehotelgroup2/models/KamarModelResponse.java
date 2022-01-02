package com.example.mobilehotelgroup2.models;

import com.google.gson.annotations.SerializedName;

public class  KamarModelResponse {

    @SerializedName("id_kamar")
    private String idKamar;
    @SerializedName("kodehotel")
    private String kodeHotel;
    @SerializedName("typekamar")
    private String typeKamar;
    @SerializedName("hargapermalam")
    private String hargaPermalam;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("stok_kamar")
    private String stokKamar;
    @SerializedName("status")
    private String status;

    public KamarModelResponse() {
    }

    public KamarModelResponse(String idKamar, String kodeHotel, String typeKamar, String hargaPermalam, String gambar, String stokKamar, String status) {
        this.idKamar = idKamar;
        this.kodeHotel = kodeHotel;
        this.typeKamar = typeKamar;
        this.hargaPermalam = hargaPermalam;
        this.gambar = gambar;
        this.stokKamar = stokKamar;
        this.status = status;
    }

    public String getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(String idKamar) {
        this.idKamar = idKamar;
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getStokKamar() {
        return stokKamar;
    }

    public void setStokKamar(String stokKamar) {
        this.stokKamar = stokKamar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
