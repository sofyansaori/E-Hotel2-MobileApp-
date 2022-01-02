package com.example.mobilehotelgroup2.models;

import com.google.gson.annotations.SerializedName;

public class RegisterModelResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
