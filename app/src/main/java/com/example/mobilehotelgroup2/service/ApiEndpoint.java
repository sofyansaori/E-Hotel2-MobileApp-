package com.example.mobilehotelgroup2.service;

import com.example.mobilehotelgroup2.models.BookingHistoryModelResponse;
import com.example.mobilehotelgroup2.models.KamarModelResponse;
import com.example.mobilehotelgroup2.models.LoginModelResponse;
import com.example.mobilehotelgroup2.models.RegisterModelResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndpoint {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModelResponse> login(
            @Field("username") String username,
            @Field("password") String pass
    );

    @FormUrlEncoded
    @POST("regist.php")
    Call<RegisterModelResponse> register(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("transaksi.php")
    Call<RegisterModelResponse> transaction(
            @Field("transaction_type") int transactionType,
            @Field("id_transaksi") String idTransaksi,
            @Field("id_kamar") String idKamar,
            @Field("id_user") String idUser,
            @Field("checkin") String chekIn,
            @Field("checkout") String checkOut
    );

    @GET("listdata.php")
    Call<List<KamarModelResponse>> listKamar();

    @FormUrlEncoded
    @POST("booking-history.php")
    Call<List<BookingHistoryModelResponse>> bookingHistory(
            @Field("id_user")
            String userId
    );
}
