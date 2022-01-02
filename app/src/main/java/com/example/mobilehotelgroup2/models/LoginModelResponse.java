package com.example.mobilehotelgroup2.models;

import com.google.gson.annotations.SerializedName;

public class LoginModelResponse {

    @SerializedName("id_user")
    private String idUser;
    @SerializedName("username")
    private String username;
    @SerializedName("pass")
    private String pass;

    public LoginModelResponse() {
    }

    public LoginModelResponse(String idUser, String username, String pass) {
        this.idUser = idUser;
        this.username = username;
        this.pass = pass;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}